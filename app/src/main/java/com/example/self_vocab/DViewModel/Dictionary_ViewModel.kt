package com.example.self_vocab.DViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.self_vocab.Entity.DictionaryEntry
import com.example.self_vocab.data.DictionaryDao
import kotlinx.coroutines.launch

class Dictionary_ViewModel(private val dictionaryDao: DictionaryDao) : ViewModel() {
var wordList = mutableStateListOf<DictionaryEntry>()


    init {
        fetchWords()
    }
    fun insertWord(word: String, meaning: String, sentence: String) {
        val newEntry = DictionaryEntry(word = word, meaning = meaning, sentence = sentence)
        viewModelScope.launch {
            dictionaryDao.insertWord(newEntry)
            fetchWords()
        }
    }
    fun fetchWords() {
        viewModelScope.launch {
            val wordsFromDB = dictionaryDao.getAllWords()
            wordList.clear()
            wordList.addAll(wordsFromDB)
        }
    }

    fun deleteWord(entry: DictionaryEntry) {
        viewModelScope.launch {
            dictionaryDao.deleteWord(entry)
            fetchWords()
        }
    }
}