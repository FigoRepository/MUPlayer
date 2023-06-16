package com.example.muplayer

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: String): String = "detail/$id"
    }
    object About : Screen("about") {
        fun createRoute(): String = "about"
    }
}