package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Disjunction : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            '|' -> lexer.yieldToken(TokenType.DisjunctionOperator, "||")
            else -> lexer.yieldUnexpectedCharError(char)
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldUnexpectedEOFError()
    }
}
