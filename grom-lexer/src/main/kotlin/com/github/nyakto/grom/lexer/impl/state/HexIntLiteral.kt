package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object HexIntLiteral : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in '0'..'9', in 'a'..'f', in 'A'..'F' -> {
                lexer.appendToBuffer(char)
            }
            '_' -> {
                lexer.continueToken(HexIntLiteralUnderscore)
            }
            'L' -> {
                lexer.yieldLongToken(16)
            }
            else -> {
                lexer.yieldIntToken(16)
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldIntToken(16)
    }
}
