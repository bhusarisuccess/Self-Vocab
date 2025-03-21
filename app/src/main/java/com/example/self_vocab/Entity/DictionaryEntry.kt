package com.example.self_vocab.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    val meaning: String,
    val sentence: String
)