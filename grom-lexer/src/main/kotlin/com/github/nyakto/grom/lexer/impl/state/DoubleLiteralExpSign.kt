package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object DoubleLiteralExpSign : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in '0'..'9' -> {
                lexer.continueToken(DoubleLiteralExpValue)
                lexer.appendToBuffer(char)
            }
            else -> lexer.yieldUnexpectedCharError(char)
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldUnexpectedEOFError()
    }
}
