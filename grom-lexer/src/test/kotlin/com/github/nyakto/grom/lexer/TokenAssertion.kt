package com.github.nyakto.grom.lexer

interface TokenAssertion {
    fun description(token: Token): String

    fun description(): String

    fun check(token: Token): Boolean
}
