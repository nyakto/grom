package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object QuestionMark : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            '.' -> lexer.yieldToken(TokenType.QuestionMarkDotOperator, "?.")
            ':' -> lexer.yieldToken(TokenType.ElvisOperator, "?:")
            else -> {
                lexer.yieldToken(TokenType.QuestionMarkOperator, "?")
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldToken(TokenType.QuestionMarkOperator, "?")
    }
}
