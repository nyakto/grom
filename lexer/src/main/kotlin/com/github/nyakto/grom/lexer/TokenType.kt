package com.github.nyakto.grom.lexer

enum class TokenType {
    Whitespace,
    Word,

    Int,
    Long,
    Float,
    Double,

    ModelKeyword,
    ViewKeyword,

    LeftBrace,
    RightBrace,
    LeftParenthesis,
    RightParenthesis,

    DotOperator
}
