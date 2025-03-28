package com.example.self_vocab.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen() {
    val showDialog = remember {
        mutableStateOf(false)
    }
    val onFabClick: () -> Unit = { showDialog.value = true }
    val onDismiss: () -> Unit = { showDialog.value = false }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FAB(
        showDialog: Boolean,
        onFabClick: () -> Unit,
        onDismiss: () -> Unit,
        onAddChat: (String) -> Unit
    ) {
        val addWord = remember {
            mutableStateOf("")
        }
        if (showDialog)
            AlertDialog(
                onDismissRequest = {
                    onDismiss.invoke()
                    addWord.value = ""
                },
                confirmButton = {
                    Button(onClick = {
                        onAddChat(addWord.value)
                        addWord.value = ""
                    }) {
                        Text(text = "Add Chat")

                    }
                },

                title = { Text(text = "Add Chat") },
                text = {
                    OutlinedTextField(
                        value = addWord.value,
                        onValueChange = { addWord.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                })


        FloatingActionButton(
            onClick = onFabClick,
            containerColor = MaterialTheme.colorScheme.secondary,
            shape = CircleShape,
            modifier = Modifier.padding(bottom = 40.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                tint = Color.White
            )

        }

    }
}




@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}