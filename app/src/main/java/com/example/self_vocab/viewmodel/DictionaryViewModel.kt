package com.example.self_vocab.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.self_vocab.data.database.Word
import com.example.self_vocab.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {
    private val _allWordList = MutableStateFlow<List<Word>>(emptyList())
    val allWordList: StateFlow<List<Word>> get() = _allWordList

    private val _latestWord = MutableStateFlow<List<Word>>(emptyList())
    val latestWord: StateFlow<List<Word>> get() = _latestWord

    private val _searchWordList = MutableStateFlow<List<Word>>(emptyList())
    val searchWordList: StateFlow<List<Word>> get() = _searchWordList

    fun addWord(word: String, meaning: String) {
        viewModelScope.launch {
            repository.insertWord(Word(word = word, meaning = meaning))
        }
    }

    fun getAllWords() {
        viewModelScope.launch {
            repository.getAllWords().collect { _allWordList.value = it }
        }
    }

    fun getLatestWord(){
        viewModelScope.launch {
            repository.getLatestWord().collect { _latestWord.value = it }
        }
    }

    fun deleteWord(wordEntry: Word){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWord(wordEntry)
        }
    }
    fun searchWord(query: String){
        viewModelScope.launch {
            repository.searchWords(query).collect { _searchWordList.value = it }
        }
    }
}
