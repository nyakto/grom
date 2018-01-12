package com.github.nyakto.grom.lexer

data class Token(
    val line: Int,
    val column: Int,
    val type: TokenType,
    val value: String
)
