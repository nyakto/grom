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
    LeftBracket,
    RightBracket,
    LeftParenthesis,
    RightParenthesis,

    DotOperator,
    CommaOperator,
    IncrementOperator,
    DecrementOperator,
    QuestionMarkOperator,
    QuestionMarkDotOperator,
    MinusOperator,
    PlusOperator,
    NotOperator,
    ColonOperator,
    MultiplyOperator,
    DivideOperator,
    RemainderOperator,
    ElvisOperator,
    LessOperator,
    GreaterOperator,
    LessOrEqualsOperator,
    GreaterOrEqualsOperator,
    EqualsOperator,
    NotEqualsOperator,
    ConjunctionOperator,
    DisjunctionOperator,
    AssignOperator,
    PlusAssignOperator,
    MinusAssignOperator,
    MultiplyAssignOperator,
    DivideAssignOperator,
    RemainderAssignOperator
}
