package com.github.nyakto.grom.lexer.impl

import com.github.nyakto.grom.lexer.*
import com.github.nyakto.grom.lexer.Lexer
import com.github.nyakto.grom.lexer.impl.state.Initial
import java.io.Reader
import java.nio.CharBuffer

internal class Lexer(
    private val listener: LexerListener
) : Lexer {
    private var state: State = Initial
    private var line: Int = 1
    private var column: Int = 1
    private var startLine = line
    private var startColumn = column
    private var buffer = StringBuilder(256)

    override fun lex(reader: Reader) {
        val buffer = CharBuffer.allocate(1024)
        var count = reader.read(buffer)
        while (count > 0) {
            (0 until count).forEach {
                state.onChar(this, buffer[it])
            }
            buffer.clear()
            count = reader.read(buffer)
        }
        state.onEOF(this)
    }

    internal fun beginToken(state: State) {
        this.state = state
        startLine = line
        startColumn = column
    }

    internal fun handle(char: Char) {
        state.onChar(this, char)
    }

    internal fun appendToBuffer(char: Char) {
        buffer.append(char)
    }

    internal fun move(char: Char) {
        if (char == '\n') {
            moveToNewLine()
        } else {
            move()
        }
    }

    internal fun move() {
        column++
    }

    private fun moveToNewLine() {
        line++
        column = 1
    }

    private fun reset() {
        beginToken(Initial)
        buffer.setLength(0)
    }

    inline private fun yieldToken(
        consumer: (Lexer, Token) -> Unit = listener::onToken,
        createToken: () -> Token
    ) {
        val token = createToken()
        reset()
        consumer(this, token)
    }

    internal fun yieldWhitespaceToken() {
        yieldToken(listener::onWhitespaceToken) {
            Token(startLine, startColumn, TokenType.Whitespace, buffer.toString())
        }
    }

    internal fun yieldToken(type: TokenType, value: String = buffer.toString()) {
        yieldToken {
            Token(startLine, startColumn, type, value)
        }
    }

    internal fun yieldWordToken() {
        yieldToken {
            val word = buffer.toString()
            Token(startLine, startColumn, Keywords[word] ?: TokenType.Word, word)
        }
    }

    internal fun yieldUnexpectedCharError(char: Char) {
        throw LexerException(
            "unexpected char '$char' at line $line, column $column",
            line, column
        )
    }

    internal fun yieldUnexpectedEOFError() {
        throw LexerException(
            "unexpected EOF at line $line, column $column",
            line, column
        )
    }
}
