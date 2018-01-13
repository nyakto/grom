package com.github.nyakto.grom.lexer

import java.io.File
import java.io.Reader
import java.io.StringReader
import java.nio.charset.Charset

interface Lexer {
    fun lex(source: String) {
        lex(StringReader(source))
    }

    fun lex(source: File, charset: Charset = Charsets.UTF_8) {
        source.bufferedReader(charset).use { reader ->
            lex(reader)
        }
    }

    fun lex(reader: Reader)
}
