package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object BinaryIntLiteralUnderscore : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            '0', '1' -> {
                lexer.continueToken(BinaryIntLiteral)
                lexer.appendToBuffer(char)
            }
            '_' -> {
            }
            else -> lexer.yieldUnexpectedCharError(char)
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldUnexpectedEOFError()
    }
}
