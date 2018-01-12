package com.github.nyakto.grom.lexer.impl

class LexerException(
    override val message: String,
    val line: Int,
    val column: Int
) : Exception()
