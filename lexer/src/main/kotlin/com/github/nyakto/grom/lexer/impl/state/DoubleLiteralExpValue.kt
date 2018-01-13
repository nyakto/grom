package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object DoubleLiteralExpValue : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in '0'..'9' -> {
                lexer.appendToBuffer(char)
            }
            '_' -> {
                lexer.continueToken(DoubleLiteralExpValueUnderscore)
            }
            else -> {
                lexer.yieldToken(TokenType.Double)
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldToken(TokenType.Double)
    }
}
