package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Zero : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            '.' -> {
                lexer.continueToken(IntDot)
            }
            'b', 'B' -> {
                lexer.continueToken(BinaryIntPrefix)
                lexer.clearBuffer()
            }
            'x', 'X' -> {
                lexer.continueToken(HexIntPrefix)
                lexer.clearBuffer()
            }
            'L' -> {
                lexer.yieldToken(TokenType.Long)
            }
            'f', 'F' -> {
                lexer.yieldToken(TokenType.Float)
            }
            else -> {
                lexer.yieldToken(TokenType.Int)
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldToken(TokenType.Int)
    }
}
