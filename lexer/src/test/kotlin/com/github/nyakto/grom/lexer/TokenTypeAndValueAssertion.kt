package com.github.nyakto.grom.lexer

class TokenTypeAndValueAssertion(
    private val type: TokenType,
    private val value: String
) : TokenAssertion {
    override fun tokenDescription(token: Token) = "${token.type}(${token.value})"

    override fun description() = "$type($value)"

    override fun check(token: Token) = token.type == type && token.value == value
}
