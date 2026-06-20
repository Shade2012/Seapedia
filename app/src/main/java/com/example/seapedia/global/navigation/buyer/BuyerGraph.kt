package com.example.seapedia.global.navigation.buyer

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.auth.AuthRoute
import com.example.seapedia.global.utils.session.SessionState

import com.example.seapedia.presentation.buyer.home.HomeBuyerScreen
import com.example.seapedia.presentation.buyer.main.MainBuyerScreen
import com.example.seapedia.presentation.buyer.profile.ProfileBuyerScreen

fun NavGraphBuilder.buyerGraph(
    navController: NavHostController,
){
    navigation(
        route = NavGraph.BUYER,
        startDestination = BuyerRoute.MainNavigation.route
    ){
        composable (BuyerRoute.MainNavigation.route){
            MainBuyerScreen(rootNavController = navController)
        }
    }
}