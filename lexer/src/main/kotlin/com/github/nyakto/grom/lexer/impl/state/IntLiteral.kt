package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object IntLiteral : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in '0'..'9' -> {
                lexer.appendToBuffer(char)
            }
            '.' -> {
                lexer.continueToken(IntDot)
            }
            '_' -> {
                lexer.continueToken(IntLiteralUnderscore)
            }
            'L' -> {
                lexer.yieldToken(TokenType.Long)
            }
            'e', 'E' -> {
                lexer.continueToken(DoubleLiteralExp)
                lexer.appendToBuffer(char)
            }
            'f', 'F' -> {
                lexer.yieldToken(TokenType.Float)
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
