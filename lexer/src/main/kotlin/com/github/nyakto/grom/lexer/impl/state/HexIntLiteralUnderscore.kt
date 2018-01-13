package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object HexIntLiteralUnderscore : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in '0'..'9', in 'a'..'f', in 'A'..'F' -> {
                lexer.continueToken(HexIntLiteral)
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
