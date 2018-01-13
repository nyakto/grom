package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object IntLiteral : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when {
            char.isDigit() -> {
                lexer.appendToBuffer(char)
            }
            char == '.' -> {
                TODO()
            }
            char == '_' -> {
                lexer.continueToken(IntLiteralUnderscore)
            }
            char == 'L' -> {
                lexer.yieldToken(TokenType.Long)
            }
            char == 'f' || char == 'F' -> {
                TODO()
            }
            else -> {
                lexer.yieldWordToken()
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldToken(TokenType.Int)
    }
}
