package com.github.nyakto.grom.lexer

object Keywords : Iterable<String> {
    private val map = hashMapOf(
        "model" to TokenType.ModelKeyword,
        "view" to TokenType.ViewKeyword
    )

    operator fun get(word: String): TokenType? = map[word]

    override fun iterator() = map.keys.iterator()
}
