package com.example.self_vocab.DViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.self_vocab.Entity.WordEntry
import com.example.self_vocab.data.WordDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DictionaryViewModel(database: WordDatabase) : ViewModel() {
    private val wordDao = database.wordDao()
    private val _words = MutableStateFlow<List<WordEntry>>(emptyList())
    val words: StateFlow<List<WordEntry>> = _words

    init {
        viewModelScope.launch {
            wordDao.getAllWords().collectLatest { wordList ->
                _words.value = wordList
            }
        }
    }
    fun addWord(word: String, meaning: String, sentence: String) {
        viewModelScope.launch {
            wordDao.insertWord(WordEntry(word = word, meaning = meaning, sentence = sentence))
        }
    }
    fun deleteWord(wordEntry: WordEntry) {
        viewModelScope.launch {
            wordDao.deleteWords(wordEntry)
        }
    }
}