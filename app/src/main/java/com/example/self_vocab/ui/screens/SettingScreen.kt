package com.example.self_vocab.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import com.example.self_vocab.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController) {
    Scaffold(
        content = { paddingValues ->
           // ListScreenContent(navController, paddingValues)
        }
    )
}