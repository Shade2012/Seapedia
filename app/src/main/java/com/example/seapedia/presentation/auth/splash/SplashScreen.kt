package com.example.seapedia.presentation.auth.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.auth.AuthRoute
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.common.IconApp

@Composable
fun SplashScreen(
        modifier: Modifier = Modifier,
        navController: NavController,
        splashViewModel: SplashScreenViewModel = hiltViewModel<SplashScreenViewModel>(),
    ) {
    val isLoading by splashViewModel.isLoading.collectAsStateWithLifecycle()
    val sessionState by splashViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(isLoading) {
        if (isLoading) return@LaunchedEffect
        if (!sessionState.isLoggedIn){
            navController.navigate(AuthRoute.Login.route) {
                popUpTo(0)
            }
            return@LaunchedEffect
        }
        when (sessionState.role) {
            null -> {
                navController.navigate(NavGraph.BUYER){
                    navController.navigate(AuthRoute.Login.route) {
                        popUpTo(0)
                    }
                }
            }
            UserRole.Guest -> {
                navController.navigate(NavGraph.BUYER){
                    popUpTo(NavGraph.AUTH){
                        inclusive= true
                    }
                }
            }
            UserRole.Buyer -> {
                navController.navigate(NavGraph.BUYER){
                    popUpTo(NavGraph.AUTH){
                        inclusive= true
                    }
                }
            }
            UserRole.Seller -> {
                navController.navigate(NavGraph.SELLER)
            }
            UserRole.Driver -> {
                navController.navigate(NavGraph.DRIVER)
            }
            UserRole.Admin -> {
                navController.navigate(NavGraph.ADMIN)
            }
        }
    }
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconApp()
    }
}
