package com.example.seapedia.global.navigation.auth

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.utils.session.SessionState
import com.example.seapedia.presentation.auth.login.LoginScreen
import com.example.seapedia.presentation.auth.register.RegisterScreen
import com.example.seapedia.presentation.auth.splash.SplashScreen

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
){
    navigation(
        route = NavGraph.AUTH,
        startDestination = AuthRoute.Splash.route
    ){
        composable(AuthRoute.Splash.route) {
            SplashScreen(Modifier,navController)
        }
        composable(AuthRoute.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(AuthRoute.Register.route) {
            RegisterScreen(navController = navController)
        }
    }
}