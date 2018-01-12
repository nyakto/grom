package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Word : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when {
            char.isLetterOrDigit() -> {
                lexer.appendToBuffer(char)
                lexer.move()
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
