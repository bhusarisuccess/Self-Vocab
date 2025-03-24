package com.example.self_vocab.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.self_vocab.DViewModel.DictionaryViewModel
import com.example.self_vocab.Entity.WordEntry
import com.example.self_vocab.data.WordDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val database = WordDatabase.getDatabase(LocalContext.current)
    val viewModel: DictionaryViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DictionaryViewModel(database) as T
        }
    })
    val latestWord by viewModel.latestWord.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Home") }) },
        content = { paddingValues ->
            HomeContent(paddingValues, latestWord)
        }
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues, latestWord: WordEntry?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        latestWord?.let {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Word: ${it.word}", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = "Meaning: ${it.meaning}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Sentence: ${it.sentence}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        } ?: Text("No words added yet")

    }
}


