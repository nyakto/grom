package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object IntLiteralUnderscore : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when {
            char.isDigit() -> {
                lexer.continueToken(IntLiteral)
                lexer.appendToBuffer(char)
            }
            char == '_' -> {
            }
            else -> lexer.yieldUnexpectedCharError(char)
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldUnexpectedEOFError()
    }
}
