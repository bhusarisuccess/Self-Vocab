package com.example.self_vocab.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.self_vocab.DViewModel.DictionaryViewModel
import com.example.self_vocab.Entity.WordEntry
import com.example.self_vocab.data.WordDatabase
import com.example.self_vocab.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController) {
    var showSheet = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(title = { Text("List") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Add Word") },
                onClick = {
                    showSheet.value = true
                },
                containerColor = PrimaryColor,
                contentColor = Color.White,
                icon = { Icon(Icons.Filled.Add, "") },
                modifier = Modifier.padding(bottom = 80.dp)
            )
        },
        content = { paddingValues ->
            ListScreenContent(navController, paddingValues, showSheet)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    showSheet: MutableState<Boolean>
) {
    val database: WordDatabase = WordDatabase.getDatabase(LocalContext.current)
    val viewModel: DictionaryViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DictionaryViewModel(database) as T
        }
    })
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val words by viewModel.words.collectAsState()
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(20.dp)
        ) {
            SearchBar(searchText = searchText, onSearchTextChanged = { searchText = it })
            Spacer(modifier = Modifier.height(5.dp))

            WordList(words = words, viewModel = viewModel)
        }
        Column(
            modifier = Modifier.align(Alignment.BottomEnd),
            verticalArrangement = Arrangement.Bottom
        ) {
            PartialBottomSheet(
                showSheet,
                onSave = { word, meaning, sentence ->
                    viewModel.addWord(word, meaning, sentence)
                    showSheet.value = false
                }
            )
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()

    )
}

@Composable
fun WordList(words: List<WordEntry>, viewModel: DictionaryViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        items(words) { word ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Word: ${word.word}", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "Meaning: ${word.meaning}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Sentence: ${word.sentence}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Row {
                        Button(onClick = { viewModel.deleteWord(word) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(showSheet: MutableState<Boolean>, onSave: (String, String, String) -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    var wordState by remember { mutableStateOf("") }
    var meaningState by remember { mutableStateOf("") }
    var sentenceState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showSheet.value) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showSheet.value = false }
            ) {
                Text(
                    "Swipe up to open sheet. Swipe down to dismiss.",
                    modifier = Modifier.padding(16.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = wordState,
                        onValueChange = { wordState = it },
                        label = { Text("Add Word") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = meaningState,
                        onValueChange = { meaningState = it },
                        label = { Text("Add Meaning") },
                        modifier = Modifier.fillMaxWidth()


                    )
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = sentenceState,
                        onValueChange = { sentenceState = it },
                        label = { Text("Add Sentence") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { showSheet.value = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                onSave(wordState, meaningState, sentenceState)
                                showSheet.value = false
                                wordState = ""
                                meaningState = ""
                                sentenceState = ""
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Add")
                        }
                    }

                }
            }
        }
    }
}
