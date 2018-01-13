package com.github.nyakto.grom.lexer

import com.github.nyakto.grom.lexer.TokenAssertionsChecker.assertTokens
import org.junit.Test

class LexerTests {
    @Test
    fun `example model declaration`() {
        """
            model {
            }
        """.assertTokens {
            token(TokenType.ModelKeyword)
            token(TokenType.LeftBrace)
            token(TokenType.RightBrace)
        }
    }

    @Test
    fun `example view declaration`() {
        """
            view {
            }
        """.assertTokens {
            token(TokenType.ViewKeyword)
            token(TokenType.LeftBrace)
            token(TokenType.RightBrace)
        }
    }

    @Test
    fun `keywords have to be recognized`() {
        "model".assertTokens {
            token(TokenType.ModelKeyword)
        }
        "view".assertTokens {
            token(TokenType.ViewKeyword)
        }

        Keywords.forEach { keyword ->
            keyword.assertTokens {
                token(Keywords[keyword]!!)
            }
        }
    }

    @Test
    fun `int literals have to be recognized`() {
        "0".assertTokens { token(TokenType.Int, "0") }
        "123".assertTokens { token(TokenType.Int, "123") }
        "123_456".assertTokens { token(TokenType.Int, "123456") }
        "0b101".assertTokens { token(TokenType.Int, "5") }
        "0B1000".assertTokens { token(TokenType.Int, "8") }
        "0B1_000".assertTokens { token(TokenType.Int, "8") }
        "0B1_0_0_0".assertTokens { token(TokenType.Int, "8") }
        "0x123".assertTokens { token(TokenType.Int, "291") }
        "0XfA__3".assertTokens { token(TokenType.Int, "4003") }
        "0Xf_A_3".assertTokens { token(TokenType.Int, "4003") }
    }

    @Test
    fun `long literals have to be recognized`() {
        "0L".assertTokens { token(TokenType.Long, "0") }
        "123L".assertTokens { token(TokenType.Long, "123") }
        "123_456L".assertTokens { token(TokenType.Long, "123456") }
        "0b101L".assertTokens { token(TokenType.Long, "5") }
        "0B1000L".assertTokens { token(TokenType.Long, "8") }
        "0B1_000L".assertTokens { token(TokenType.Long, "8") }
        "0B1_0_0_0L".assertTokens { token(TokenType.Long, "8") }
        "0x123L".assertTokens { token(TokenType.Long, "291") }
        "0XfA__3L".assertTokens { token(TokenType.Long, "4003") }
        "0Xf_A_3L".assertTokens { token(TokenType.Long, "4003") }
    }

    @Test
    fun `double literals have to be recognized`() {
        ".0".assertTokens { token(TokenType.Double, "0.0") }
        "0.0".assertTokens { token(TokenType.Double, "0.0") }
        ".123".assertTokens { token(TokenType.Double, "0.123") }
        "0.123".assertTokens { token(TokenType.Double, "0.123") }
        "0.123_456".assertTokens { token(TokenType.Double, "0.123456") }
        "10e-5".assertTokens { token(TokenType.Double, "10e-5") }
        "10e-5_0".assertTokens { token(TokenType.Double, "10e-50") }
        "10e+50".assertTokens { token(TokenType.Double, "10e+50") }
    }

    @Test
    fun `float literals have to be recognized`() {
        "123F".assertTokens { token(TokenType.Float, "123") }
        ".0f".assertTokens { token(TokenType.Float, "0.0") }
        "0.0f".assertTokens { token(TokenType.Float, "0.0") }
        ".123f".assertTokens { token(TokenType.Float, "0.123") }
        "0.123f".assertTokens { token(TokenType.Float, "0.123") }
        "0.123_456f".assertTokens { token(TokenType.Float, "0.123456") }
        "10e-5f".assertTokens { token(TokenType.Float, "10e-5") }
        "10e-5_0f".assertTokens { token(TokenType.Float, "10e-50") }
        "10e+50f".assertTokens { token(TokenType.Float, "10e+50") }
    }

    @Test
    fun `can recognize word after number with dot`() {
        "5.times(100)".assertTokens {
            token(TokenType.Int, "5")
            token(TokenType.DotOperator, ".")
            token(TokenType.Word, "times")
            token(TokenType.LeftParenthesis, "(")
            token(TokenType.Int, "100")
            token(TokenType.RightParenthesis, ")")
        }
    }

    @Test
    fun `can recognize operators`() {
        "+".assertTokens { token(TokenType.PlusOperator, "+") }
        "++".assertTokens { token(TokenType.IncrementOperator, "++") }
        "+=".assertTokens { token(TokenType.PlusAssignOperator, "+=") }
        "-".assertTokens { token(TokenType.MinusOperator, "-") }
        "--".assertTokens { token(TokenType.DecrementOperator, "--") }
        "-=".assertTokens { token(TokenType.MinusAssignOperator, "-=") }
        "!".assertTokens { token(TokenType.NotOperator, "!") }
        "!=".assertTokens { token(TokenType.NotEqualsOperator, "!=") }
        "*".assertTokens { token(TokenType.MultiplyOperator, "*") }
        "*=".assertTokens { token(TokenType.MultiplyAssignOperator, "*=") }
        "/".assertTokens { token(TokenType.DivideOperator, "/") }
        "/=".assertTokens { token(TokenType.DivideAssignOperator, "/=") }
        "%".assertTokens { token(TokenType.RemainderOperator, "%") }
        "%=".assertTokens { token(TokenType.RemainderAssignOperator, "%=") }
        "?".assertTokens { token(TokenType.QuestionMarkOperator, "?") }
        "?.".assertTokens { token(TokenType.QuestionMarkDotOperator, "?.") }
        "?:".assertTokens { token(TokenType.ElvisOperator, "?:") }
        "<".assertTokens { token(TokenType.LessOperator, "<") }
        "<=".assertTokens { token(TokenType.LessOrEqualsOperator, "<=") }
        ">".assertTokens { token(TokenType.GreaterOperator, ">") }
        ">=".assertTokens { token(TokenType.GreaterOrEqualsOperator, ">=") }
        "&&".assertTokens { token(TokenType.ConjunctionOperator, "&&") }
        "||".assertTokens { token(TokenType.DisjunctionOperator, "||") }
        "=".assertTokens { token(TokenType.AssignOperator, "=") }
        "==".assertTokens { token(TokenType.EqualsOperator, "==") }
    }
}
