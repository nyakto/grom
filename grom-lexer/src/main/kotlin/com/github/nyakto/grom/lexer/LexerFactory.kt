package com.github.nyakto.grom.lexer

import com.github.nyakto.grom.lexer.impl.Lexer

object LexerFactory {
    fun createLexer(listener: LexerListener): com.github.nyakto.grom.lexer.Lexer = Lexer(listener)
}
