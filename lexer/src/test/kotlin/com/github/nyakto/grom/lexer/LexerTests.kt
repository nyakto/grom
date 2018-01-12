package com.github.nyakto.grom.lexer

import org.junit.Assert
import org.junit.Test

class LexerTests {
    private fun expectError(
        expectedError: Throwable,
        block: () -> Unit
    ) {
        var failed = true
        try {
            block()
            failed = false
        } catch (actualError: Throwable) {
            Assert.assertTrue(
                "expected ${expectedError::class.simpleName}, but got ${actualError::class.simpleName} exception",
                actualError::class == expectedError::class
            )
            Assert.assertEquals(expectedError.message, actualError.message)
        }
        Assert.assertTrue("no errors occured, but ${expectedError::class.simpleName} expected", failed)
    }

    @Test
    fun `checkTokens have to check size`() {
        expectError(AssertionError("expected less tokens")) {
            checkTokens("model")
        }
        expectError(AssertionError("expected more tokens")) {
            checkTokens("", TokenType.ModelKeyword)
        }
    }

    @Test
    fun `example model declaration`() {
        checkTokens(
            """
                model {
                }
            """,
            TokenType.ModelKeyword,
            TokenType.LeftBrace,
            TokenType.RightBrace
        )
    }

    @Test
    fun `example view declaration`() {
        checkTokens(
            """
                view {
                }
            """,
            TokenType.ViewKeyword,
            TokenType.LeftBrace,
            TokenType.RightBrace
        )
    }

    @Test
    fun `keywords have to be recognized`() {
        checkTokens("model", TokenType.ModelKeyword)
        checkTokens("view", TokenType.ViewKeyword)

        Keywords.forEach { keyword ->
            checkTokens(keyword, Keywords[keyword]!!)
        }
    }

    private fun <Source, Item> checkTokens(
        source: Source,
        lex: Lexer.(Source) -> Unit,
        ignoreWhitespace: Boolean,
        itemGetter: (Token) -> Item,
        vararg items: Item
    ) {
        val iterator = items.iterator()
        val lexer = LexerFactory.createLexer(object : LexerListener {
            override fun onToken(lexer: Lexer, token: Token) {
                Assert.assertTrue("expected less tokens", iterator.hasNext())
                val expected = iterator.next()
                val actual = itemGetter(token)
                Assert.assertEquals(expected, actual)
            }

            override fun onWhitespaceToken(lexer: Lexer, token: Token) {
                if (!ignoreWhitespace) {
                    onToken(lexer, token)
                }
            }
        })
        lex(lexer, source)
        Assert.assertFalse("expected more tokens", iterator.hasNext())
    }

    private fun checkTokens(
        source: String,
        vararg types: TokenType
    ) {
        checkTokens(source, Lexer::lex, true, { it.type }, *types)
    }
}
