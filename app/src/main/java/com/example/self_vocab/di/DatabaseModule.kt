package com.example.self_vocab.di

import android.app.Application
import androidx.room.Room
import com.example.self_vocab.data.database.DictionaryDatabase
import com.example.self_vocab.data.database.WordDao
import com.example.self_vocab.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): DictionaryDatabase {
        return Room.databaseBuilder(
            app,
            DictionaryDatabase::class.java,
            "dictionary_db"
        ).build()
    }

    @Provides
    fun provideWordDao(database: DictionaryDatabase): WordDao {
        return database.wordDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(wordDao: WordDao): DatabaseRepository {
        return DatabaseRepository(wordDao)
    }
}