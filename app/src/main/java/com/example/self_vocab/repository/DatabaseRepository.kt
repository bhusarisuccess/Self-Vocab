package com.example.self_vocab.repository

import android.content.Context
import com.example.self_vocab.data.database.Word
import com.example.self_vocab.data.database.WordDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(
    private val wordDao: WordDao,
    @ApplicationContext private val context: Context
) {
    suspend fun insertWord(word: Word) = wordDao.insertWord(word)
    fun getAllWords(): Flow<List<Word>> = wordDao.getAllWords()
    fun searchWords(query: String): Flow<List<Word>> = wordDao.searchWords("%$query%")
    fun getLatestWord(): Flow<List<Word>> = wordDao.getAllWords()
    fun deleteWord(wordEntry: Word) {
        wordDao.deleteWords(wordEntry)
    }

    suspend fun getRandomWord(): Word? = wordDao.getRandomWord()
}