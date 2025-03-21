package com.example.self_vocab.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.self_vocab.Entity.WordEntry


@Database(entities = [WordEntry::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: android.content.Context): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "Vocab_Database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
