package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Initial : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in 'a'..'z', in 'A'..'Z' -> {
                lexer.beginToken(Word)
                lexer.appendToBuffer(char)
            }
            ' ', '\t', '\r', '\n' -> {
                char.isWhitespace()
                lexer.beginToken(Whitespace)
                lexer.appendToBuffer(char)
            }
            '0' -> {
                lexer.beginToken(Zero)
                lexer.appendToBuffer(char)
            }
            in '0'..'9' -> {
                lexer.beginToken(IntLiteral)
                lexer.appendToBuffer(char)
            }
            '.' -> {
                lexer.beginToken(Dot)
            }
            '{' -> {
                lexer.yieldToken(TokenType.LeftBrace, "{")
            }
            '}' -> {
                lexer.yieldToken(TokenType.RightBrace, "}")
            }
            else -> lexer.yieldUnexpectedCharError(char)
        }
    }

    override fun onEOF(lexer: Lexer) {
    }
}
