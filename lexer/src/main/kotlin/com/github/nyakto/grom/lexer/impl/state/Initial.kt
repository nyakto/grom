package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Initial : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when {
            char.isLetter() -> {
                lexer.beginToken(Word)
                lexer.appendToBuffer(char)
                lexer.move()
            }
            char.isWhitespace() -> {
                lexer.beginToken(Whitespace)
                lexer.appendToBuffer(char)
                lexer.move(char)
            }
            char == '{' -> {
                lexer.yieldToken(TokenType.LeftBrace, "{")
                lexer.move()
            }
            char == '}' -> {
                lexer.yieldToken(TokenType.RightBrace, "}")
                lexer.move()
            }
            else -> lexer.yieldUnexpectedCharError(char)
        }
    }

    override fun onEOF(lexer: Lexer) {
    }
}
