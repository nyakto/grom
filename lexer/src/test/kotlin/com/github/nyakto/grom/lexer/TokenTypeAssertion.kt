package com.github.nyakto.grom.lexer

class TokenTypeAssertion(
    private val type: TokenType
) : TokenAssertion {
    override fun tokenDescription(token: Token) = token.type.toString()

    override fun description() = type.toString()

    override fun check(token: Token) = token.type == type
}
