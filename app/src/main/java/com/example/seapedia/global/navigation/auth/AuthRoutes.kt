package com.example.seapedia.global.navigation.auth


sealed class AuthRoute(val route: String,val name: String){
    object Splash : AuthRoute("splash","splash_screen")
    object  Login : AuthRoute("login", name = "login_screen")
    object  Register : AuthRoute("register", name = "register_screen")
}
