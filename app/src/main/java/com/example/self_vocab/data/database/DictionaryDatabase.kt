package com.example.self_vocab.data.database
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}