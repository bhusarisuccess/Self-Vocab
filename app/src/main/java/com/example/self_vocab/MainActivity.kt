package com.example.self_vocab

import android.app.FragmentManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.self_vocab.ui.screens.BottomNavigationMenu
import com.example.self_vocab.ui.screens.HomeScreen
import com.example.self_vocab.ui.screens.ListScreen
import com.example.self_vocab.ui.screens.Settingscreen
import com.example.self_vocab.ui.screens.SplashScreen
import com.example.self_vocab.ui.theme.SelfVocabTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SelfVocabTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = {
                    val navBackStackEntry = navController.currentBackStackEntryAsState().value
                    val currentRoute = navBackStackEntry?.destination?.route
                    if (currentRoute != "splash") {
                        BottomNavigationMenu(navController)
                    }
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("splash") {
                            SplashScreen(navController)
                        }
                        composable("home") {
                            HomeScreen()
                        }
                        composable("list") {
                            ListScreen()
                        }
                        composable("settings") {
                            Settingscreen()

                        }
                    }
                }
            }
        }

    }
}
