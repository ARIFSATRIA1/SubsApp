package com.example.subsapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailAnime : Screen("home/{animeId}") {
        fun createRoute(animeId: Long) = "home/$animeId"
    }
}
