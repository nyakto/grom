package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Plus : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            '+' -> lexer.yieldToken(TokenType.IncrementOperator, "++")
            '=' -> lexer.yieldToken(TokenType.PlusAssignOperator, "+=")
            else -> {
                lexer.yieldToken(TokenType.PlusOperator, "+")
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldToken(TokenType.PlusOperator, "+")
    }
}
