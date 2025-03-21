package com.example.self_vocab.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.self_vocab.Entity.WordEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert
    suspend fun insertWord(wordEntry: WordEntry)

    @Query("SELECT * FROM WordEntry")
    fun getAllWords(): Flow<List<WordEntry>>

    @Delete
    suspend fun deleteWords(wordEntry: WordEntry)
    @Query("SELECT * FROM WordEntry ORDER BY id DESC LIMIT 1")
    fun getLatestWord(): Flow<WordEntry?>
}