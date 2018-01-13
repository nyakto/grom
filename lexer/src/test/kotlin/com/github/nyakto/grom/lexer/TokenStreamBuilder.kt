package com.github.nyakto.grom.lexer

interface TokenStreamBuilder {
    fun assert(assertion: TokenAssertion)

    fun token(type: TokenType) = assert(TokenTypeAssertion(type))

    fun token(type: TokenType, value: String) = assert(TokenTypeAndValueAssertion(type, value))
}
