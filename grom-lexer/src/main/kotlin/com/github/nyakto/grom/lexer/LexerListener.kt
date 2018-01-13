package com.github.nyakto.grom.lexer

interface LexerListener {
    fun onToken(lexer: Lexer, token: Token)

    fun onWhitespaceToken(lexer: Lexer, token: Token) {
        onToken(lexer, token)
    }
}
