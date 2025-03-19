package com.example.self_vocab.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.self_vocab.ui.theme.Black
import kotlinx.coroutines.launch

@Composable
fun ListScreen() {

    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(20.dp)
        ) {
            SearchBar(searchText = searchText, onSearchTextChanged = { searchText = it })
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            PartialBottomSheet()
            Spacer(modifier = Modifier.height(16.dp))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false)
    val AddWord = remember {mutableStateOf("")}
    val wordMeaning = remember {mutableStateOf("")}
    val wordSentence = remember {mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 50.dp, end = 20.dp),
            onClick = { showBottomSheet = true }
        ) {
            Icon(Icons.Default.AddCircle, contentDescription = "Add Icon")
//            Text("Display partial bottom sheet")
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
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
                        value = AddWord.value,
                        onValueChange = { AddWord.value = it },
                        label = { Text("Add Word") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
                    OutlinedTextField(
                        value = wordMeaning.value,
                        onValueChange = { wordMeaning.value = it },
                        label = { Text("Add Meaning") },
                        modifier = Modifier.fillMaxWidth()


                    )
                    Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
                    OutlinedTextField(
                        value = wordSentence.value,
                        onValueChange = { wordSentence.value = it },
                        label = { Text("Add Sentence") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
                    Row( modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(
                            onClick = { showBottomSheet = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                            }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { showBottomSheet = true },

                            modifier = Modifier.weight(1f)){
                            Text("Add")

                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun listPreview() {
    ListScreen()
}
