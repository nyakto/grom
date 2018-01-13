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
            }
            char.isWhitespace() -> {
                lexer.beginToken(Whitespace)
                lexer.appendToBuffer(char)
            }
            char == '0' -> {
                lexer.beginToken(Zero)
                lexer.appendToBuffer(char)
            }
            char.isDigit() -> {
                lexer.beginToken(IntLiteral)
                lexer.appendToBuffer(char)
            }
            char == '.' -> {
                lexer.beginToken(Dot)
                lexer.appendToBuffer(char)
            }
            char == '{' -> {
                lexer.yieldToken(TokenType.LeftBrace, "{")
            }
            char == '}' -> {
                lexer.yieldToken(TokenType.RightBrace, "}")
            }
            else -> lexer.yieldUnexpectedCharError(char)
        }
    }

    override fun onEOF(lexer: Lexer) {
    }
}
