package com.example.self_vocab.ui.screens

import com.example.self_vocab.R

sealed class BottomNavItem(val route: String, val title: String, val icon: Int){
    object Home : BottomNavItem("home", "Home",R.drawable.home)
    object List : BottomNavItem("list", "List",R.drawable.list)
    object Settings : BottomNavItem("settings", "Settings",R.drawable.setting)
}