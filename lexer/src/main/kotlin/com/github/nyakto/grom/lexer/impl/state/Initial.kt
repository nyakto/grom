package com.github.nyakto.grom.lexer.impl.state

import com.github.nyakto.grom.lexer.TokenType
import com.github.nyakto.grom.lexer.impl.Lexer
import com.github.nyakto.grom.lexer.impl.State

internal object Initial : State {
    override fun onChar(lexer: Lexer, char: Char) {
        when (char) {
            in 'a'..'z', in 'A'..'Z' -> {
                lexer.beginToken(Word)
                lexer.appendToBuffer(char)
            }
            ' ', '\t', '\r', '\n' -> {
                char.isWhitespace()
                lexer.beginToken(Whitespace)
                lexer.appendToBuffer(char)
            }
            '0' -> {
                lexer.beginToken(Zero)
                lexer.appendToBuffer(char)
            }
            in '0'..'9' -> {
                lexer.beginToken(IntLiteral)
                lexer.appendToBuffer(char)
            }
            '.' -> lexer.beginToken(Dot)
            ',' -> lexer.yieldToken(TokenType.CommaOperator, ",")
            ':' -> lexer.yieldToken(TokenType.ColonOperator, ":")
            ';' -> lexer.yieldToken(TokenType.SemicolonOperator, ";")
            '{' -> lexer.yieldToken(TokenType.LeftBrace, "{")
            '}' -> lexer.yieldToken(TokenType.RightBrace, "}")
            '[' -> lexer.yieldToken(TokenType.LeftBracket, "[")
            ']' -> lexer.yieldToken(TokenType.RightBracket, "]")
            '(' -> lexer.yieldToken(TokenType.LeftParenthesis, "(")
            ')' -> lexer.yieldToken(TokenType.RightParenthesis, ")")
            '+' -> lexer.beginToken(Plus)
            '-' -> lexer.beginToken(Minus)
            '!' -> lexer.beginToken(Not)
            '*' -> lexer.beginToken(Multiply)
            '/' -> lexer.beginToken(Divide)
            '%' -> lexer.beginToken(Remainder)
            '?' -> lexer.beginToken(QuestionMark)
            '<' -> lexer.beginToken(Less)
            '>' -> lexer.beginToken(Greater)
            '&' -> lexer.beginToken(Conjunction)
            '|' -> lexer.beginToken(Disjunction)
            '=' -> lexer.beginToken(Assign)
            else -> lexer.yieldUnexpectedCharError(char)
        }
    }

    override fun onEOF(lexer: Lexer) {
    }
}
