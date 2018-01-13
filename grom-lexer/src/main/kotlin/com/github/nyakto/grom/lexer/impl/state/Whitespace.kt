package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Whitespace : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            ' ', '\t', '\r', '\n' -> {
                lexer.appendToBuffer(char)
            }
            else -> {
                lexer.yieldWhitespaceToken()
                lexer.handle(char)
            }
        }
    }

    override fun onEOF(lexer: Lexer) {
        lexer.yieldWhitespaceToken()
    }
}
