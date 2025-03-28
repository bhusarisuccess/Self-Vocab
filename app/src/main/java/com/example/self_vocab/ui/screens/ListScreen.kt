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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.self_vocab.data.database.Word
import com.example.self_vocab.ui.theme.PrimaryColor
import com.example.self_vocab.viewmodel.DictionaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController) {
    var showSheet = remember { mutableStateOf(false) }
    Scaffold(
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
    val viewModel: DictionaryViewModel = hiltViewModel()
    LaunchedEffect(Any()) {
        viewModel.getAllWords()
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.padding(top = 100.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(20.dp)
        ) {
            SearchBar(searchText = searchText, onSearchTextChanged = { searchText = it
            viewModel.searchWord(searchText)})
            Spacer(modifier = Modifier.height(5.dp))
            WordList(viewModel)
        }
        Column(
            modifier = Modifier.align(Alignment.BottomEnd),
            verticalArrangement = Arrangement.Bottom
        ) {
            PartialBottomSheet(
                showSheet,
                onSave = { word, meaning, sentence ->
                   viewModel.addWord(word, meaning)
                    viewModel.getAllWords()
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
fun WordList(viewModel: DictionaryViewModel) {
    val words by viewModel.allWordList.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        items(words.size) { index ->
            WordCard(word = words[index], onDelete = {
                viewModel.deleteWord(words[index]) })
        }
    }
}

@Composable
fun WordCard(word: Word, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = "Word Icon",
                    tint = PrimaryColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = word.word,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Word",
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Meaning:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
            Text(
                text = word.meaning,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Sentence:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
            Text(
                text = word.word,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF37474F)
            )
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
