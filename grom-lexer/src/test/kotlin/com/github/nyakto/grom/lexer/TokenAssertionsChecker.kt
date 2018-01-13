package com.github.nyakto.grom.lexer

import org.junit.Assert

object TokenAssertionsChecker {
    fun String.assertTokens(skipWhitespace: Boolean = true, builder: TokenAssertionsBuilder.() -> Unit) {
        val assertions = mutableListOf<TokenAssertion>()
        builder(object : TokenAssertionsBuilder {
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
                val assertion = iterator.next()
                Assert.assertTrue(
                    "expected ${assertion.description()}, but got ${assertion.description(token)} token",
                    assertion.check(token)
                )
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
