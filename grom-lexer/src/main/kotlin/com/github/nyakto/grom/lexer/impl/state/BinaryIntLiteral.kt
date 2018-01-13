package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object BinaryIntLiteral : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            '0', '1' -> {
                lexer.appendToBuffer(char)
            }
            '_' -> {
                lexer.continueToken(BinaryIntLiteralUnderscore)
            }
            'L' -> {
                lexer.yieldLongToken(2)
            }
            else -> {
                lexer.yieldIntToken(2)
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldIntToken(2)
    }
}
