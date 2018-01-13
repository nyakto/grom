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
            checkTokenTypes("model")
        }
        expectError(AssertionError("expected more tokens")) {
            checkTokenTypes("", TokenType.ModelKeyword)
        }
    }

    @Test
    fun `example model declaration`() {
        checkTokenTypes(
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
        checkTokenTypes(
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
        checkTokenTypes("model", TokenType.ModelKeyword)
        checkTokenTypes("view", TokenType.ViewKeyword)

        Keywords.forEach { keyword ->
            checkTokenTypes(keyword, Keywords[keyword]!!)
        }
    }

    @Test
    fun `int literals have to be recognized`() {
        checkTokens("0", Token(1, 1, TokenType.Int, "0"))
        checkTokens("123", Token(1, 1, TokenType.Int, "123"))
        checkTokens("123_456", Token(1, 1, TokenType.Int, "123456"))
        checkTokens("0b101", Token(1, 1, TokenType.Int, "5"))
        checkTokens("0B1000", Token(1, 1, TokenType.Int, "8"))
        checkTokens("0B1_000", Token(1, 1, TokenType.Int, "8"))
        checkTokens("0B1_0_0_0", Token(1, 1, TokenType.Int, "8"))
        checkTokens("0x123", Token(1, 1, TokenType.Int, "291"))
        checkTokens("0XfA__3", Token(1, 1, TokenType.Int, "4003"))
        checkTokens("0Xf_A_3", Token(1, 1, TokenType.Int, "4003"))
    }

    @Test
    fun `long literals have to be recognized`() {
        checkTokens("0L", Token(1, 1, TokenType.Long, "0"))
        checkTokens("123L", Token(1, 1, TokenType.Long, "123"))
        checkTokens("123_456L", Token(1, 1, TokenType.Long, "123456"))
        checkTokens("0b101L", Token(1, 1, TokenType.Long, "5"))
        checkTokens("0B1000L", Token(1, 1, TokenType.Long, "8"))
        checkTokens("0B1_000L", Token(1, 1, TokenType.Long, "8"))
        checkTokens("0B1_0_0_0L", Token(1, 1, TokenType.Long, "8"))
        checkTokens("0x123L", Token(1, 1, TokenType.Long, "291"))
        checkTokens("0XfA__3L", Token(1, 1, TokenType.Long, "4003"))
        checkTokens("0Xf_A_3L", Token(1, 1, TokenType.Long, "4003"))
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

    private fun checkTokenTypes(
        source: String,
        vararg types: TokenType
    ) {
        checkTokens(source, Lexer::lex, true, { it.type }, *types)
    }

    private fun checkTokens(
        source: String,
        vararg tokens: Token
    ) {
        checkTokens(source, Lexer::lex, true, { it }, *tokens)
    }
}
