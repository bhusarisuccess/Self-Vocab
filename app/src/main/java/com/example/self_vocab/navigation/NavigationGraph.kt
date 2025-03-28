package com.example.self_vocab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.self_vocab.ui.screens.HomeScreen
import com.example.self_vocab.ui.screens.ListScreen
import com.example.self_vocab.ui.screens.MainScreen
import com.example.self_vocab.ui.screens.SettingScreen
import com.example.self_vocab.ui.screens.SplashScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.SPLASH_SCREEN.route) {
        composable(Destination.SPLASH_SCREEN.route) {
            SplashScreen(navController)
        }
        composable(Destination.MAIN_SCREEN.route) {
            MainScreen(navController)
        }
        composable(Destination.HOME_SCREEN.route) {
            HomeScreen(navController)
        }
        composable(Destination.LIST_SCREEN.route) {
            ListScreen(navController)
        }
        composable(Destination.SETTING_SCREEN.route) {
            SettingScreen(navController)
        }
    }
}
