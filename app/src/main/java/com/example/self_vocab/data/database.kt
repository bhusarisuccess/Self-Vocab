package com.example.self_vocab.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.self_vocab.Entity.DictionaryEntry

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(entry: DictionaryEntry)

    @Query("SELECT * FROM dictionary WHERE word = :word")
    suspend fun getWord(word: String): DictionaryEntry?

    @Query("SELECT * FROM dictionary WHERE meaning = :meaning")
    suspend fun getMeaning(meaning: String): DictionaryEntry?

    @Query("SELECT * FROM dictionary WHERE sentence = :sentence")
    suspend fun getSentence(sentence: String): DictionaryEntry?


    @Query("SELECT * FROM dictionary ORDER BY word ASC")
    suspend fun getAllWords(): List<DictionaryEntry>

    @Delete
    suspend fun deleteWord(entry: DictionaryEntry)
}