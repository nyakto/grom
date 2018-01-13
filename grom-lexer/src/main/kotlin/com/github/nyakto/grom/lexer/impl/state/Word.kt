package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Word : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in 'a'..'z', in 'A'..'Z', in '0'..'9' -> {
                lexer.appendToBuffer(char)
            }
            else -> {
                lexer.yieldWordToken()
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldWordToken()
    }
}
