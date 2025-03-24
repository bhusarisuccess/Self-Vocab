package com.example.self_vocab.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.self_vocab.Destination
import com.example.self_vocab.NavigationGraph
import com.example.self_vocab.ui.theme.AppTheme


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val navController = rememberNavController()
    AppTheme {
        val items = listOf(
            BottomNavigationItem(
                title = Destination.HOME_SCREEN.route,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                hasNews = false,
            ),
            BottomNavigationItem(
                title = Destination.LIST_SCREEN.route,
                selectedIcon = Icons.Filled.Email,
                unselectedIcon = Icons.Outlined.Email,
                hasNews = false,
                badgeCount = 45
            ),
            BottomNavigationItem(
                title = Destination.SETTING_SCREEN.route,
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                hasNews = true,
            ),
        )
        var selectedIndex = remember{
            mutableIntStateOf(0)
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = { Text(text = items[selectedIndex.value].title) },
                bottomBar = {
                    BottomNavigationBar(navController, items,selectedIndex)
                }
            ) { paddingValue ->
                NavHost(navController = navController, startDestination = Destination.HOME_SCREEN.route) {
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
        }
    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavigationItem>,
    selectedIndex: MutableIntState
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    selectedIndex.value = index
                    navController.navigate(item.title){
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}

@Composable
fun MainScreenContent(name: String, paddingValue: PaddingValues) {
    Text(
        text = "Hello $name!",
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        MainScreen(rememberNavController())
    }
}