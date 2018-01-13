package com.github.nyakto.grom.lexer.impl

internal interface State {
    fun onChar(lexer: Lexer, char: Char)
    fun onEOF(lexer: Lexer)
}
