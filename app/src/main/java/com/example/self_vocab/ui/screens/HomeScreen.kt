package com.example.self_vocab.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.self_vocab.data.database.Word
import com.example.self_vocab.viewmodel.DictionaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        content = { paddingValues ->
            HomeContent(paddingValues)
        }
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
   val viewModel: DictionaryViewModel = hiltViewModel()
   val words by viewModel.latestWord.collectAsState()
    LaunchedEffect(Any()) {
        viewModel.getLatestWord()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       if(words.isNotEmpty()){
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Word: ${words[0].word}", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = "Meaning: ${words[0].meaning}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }else{
           Text("No words added yet")
       }

    }
}



