package com.github.nyakto.grom.lexer

import org.junit.Assert
import org.junit.Test

class LexerTests {
    @Test
    fun testKeywords() {
        checkTokens("model", TokenType.ModelKeyword)
        checkTokens("view", TokenType.ViewKeyword)
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
                Assert.assertTrue(iterator.hasNext())
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
        Assert.assertFalse(iterator.hasNext())
    }

    private fun checkTokens(
        source: String,
        vararg types: TokenType
    ) {
        checkTokens(source, Lexer::lex, true, { it.type }, *types)
    }
}
