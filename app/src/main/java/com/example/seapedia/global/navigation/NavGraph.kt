package com.example.seapedia.global.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seapedia.presentation.auth.login.LoginScreen
import com.example.seapedia.presentation.home.HomeScreen
import com.example.seapedia.presentation.main.MainButtonScreen
import com.example.seapedia.presentation.profile.ProfileScreen
import com.example.seapedia.presentation.splash.SplashScreen

@Composable
fun NavGraph() {
    val navController  = rememberNavController()
    val startDestination = Routes.Login.name
    val modifier = Modifier
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Routes.MainBottom.name) {
            MainButtonScreen(modifier)
        }
        composable(
            route = Routes.Home.name
        ) {
            HomeScreen(modifier)
        }

        composable(route = Routes.Login.name) {
            LoginScreen(modifier)
        }

        composable(
            route = Routes.Profile.name
        ) {
            ProfileScreen(modifier)
        }
        composable (
            route = Routes.Splash.name
        ) {
            SplashScreen(modifier)
        }
    }
}