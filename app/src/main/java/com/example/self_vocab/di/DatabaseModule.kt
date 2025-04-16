package com.example.self_vocab.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.self_vocab.data.database.DictionaryDatabase
import com.example.self_vocab.data.database.WordDao
import com.example.self_vocab.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DictionaryDatabase {
        val MIGRATION_1_2 = object : Migration(1, 2  ) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE your_table ADD COLUMN new_column_name TEXT DEFAULT ''")
            }
        }
                return Room.databaseBuilder(
                    context,
                    DictionaryDatabase::class.java,
                    "dictionary_db"
                ).fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }



    @Provides
    fun provideWordDao(database: DictionaryDatabase): WordDao = database.wordDao()

    @Provides
    @Singleton
    fun provideDatabaseRepository(wordDao: WordDao,): DatabaseRepository {
        return DatabaseRepository(wordDao)
    }
}