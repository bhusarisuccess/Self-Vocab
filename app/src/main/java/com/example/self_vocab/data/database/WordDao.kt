package com.example.self_vocab.data.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Query("SELECT * FROM dictionary ORDER BY id DESC")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM dictionary WHERE word LIKE :searchQuery")
    fun searchWords(searchQuery: String): Flow<List<Word>>

    @Delete
    fun deleteWords(wordEntry: Word)

    @Query("SELECT * FROM dictionary ORDER BY id DESC LIMIT 1")
    fun getLatestWord(): Flow<List<Word?>>
}