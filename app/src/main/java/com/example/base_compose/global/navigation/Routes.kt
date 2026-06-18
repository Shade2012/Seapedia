package com.example.base_compose.global.navigation

//@Serializable

sealed class Routes(val name:String, val path: String) {

    object Home : Routes("home","home_screen")
    object Login : Routes("login","login_screen")
    object Profile : Routes("profile","profile_screen")

    object MainBottom : Routes("mainBottom","main_bottom_screen")

    object Splash : Routes("splash","splash_screen")
}

