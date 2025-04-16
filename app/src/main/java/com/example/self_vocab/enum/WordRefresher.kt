package com.example.self_vocab.enum

enum class WordRefresher (val millis: Long)
{
    ONE_MIN(60 * 1000),
    FIFTEEN_MIN(15 * 60 * 1000),
    THIRTY_MIN(30 * 60 * 1000),
    ONE_HOUR(60 * 60 * 1000),
    TWO_HOUR(2 * 60 * 60 * 1000)
}