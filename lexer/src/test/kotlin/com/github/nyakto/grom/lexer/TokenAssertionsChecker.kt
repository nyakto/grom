package com.github.nyakto.grom.lexer

import org.junit.Assert

object TokenAssertionsChecker {
    fun String.assertTokens(skipWhitespace: Boolean = true, builder: TokenStreamBuilder.() -> Unit) {
        val assertions = mutableListOf<TokenAssertion>()
        builder(object : TokenStreamBuilder {
            override fun assert(assertion: TokenAssertion) {
                assertions.add(assertion)
            }
        })

        val iterator = assertions.iterator()
        LexerFactory.createLexer(object : LexerListener {
            override fun onToken(lexer: Lexer, token: Token) {
                Assert.assertTrue(
                    "unexpected ${token.type}(${token.value}) token, expected end of input",
                    iterator.hasNext()
                )
                iterator.next().check(token)
            }

            override fun onWhitespaceToken(lexer: Lexer, token: Token) {
                if (!skipWhitespace) {
                    onToken(lexer, token)
                }
            }
        }).lex(this)
        if (iterator.hasNext()) {
            val assertion = iterator.next()
            Assert.fail("unexpected end of input, expected ${assertion.description()} token")
        }
    }
}
