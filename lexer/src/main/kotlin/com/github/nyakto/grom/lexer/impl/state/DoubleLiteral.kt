package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object DoubleLiteral : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when {
            char.isDigit() -> {
                TODO()
            }
            else -> {
                lexer.yieldToken(TokenType.DotOperator)
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldToken(TokenType.DotOperator)
    }
}
