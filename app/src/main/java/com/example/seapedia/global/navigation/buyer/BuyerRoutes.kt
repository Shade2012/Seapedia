package com.example.seapedia.global.navigation.buyer

sealed class BuyerRoute(val route: String,val name: String){
    object Home : BuyerRoute("home_buyer","home_buyer_screen")
    object Profile : BuyerRoute("profile_buyer", name = "profile_buyer_screen")
    object MainNavigation : BuyerRoute("main_buyer", name = "main_buyer_screen")
}