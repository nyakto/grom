package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object IntDot : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in '0'..'9' -> {
                lexer.continueToken(DoubleLiteral)
                lexer.appendToBuffer('.')
                lexer.appendToBuffer(char)
            }
            else -> {
                lexer.yieldToken(TokenType.Int)
                lexer.yieldToken(TokenType.DotOperator, ".")
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldToken(TokenType.Int)
        lexer.yieldToken(TokenType.DotOperator, ".")
    }
}
