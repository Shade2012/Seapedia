package com.example.seapedia.global.navigation.driver

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.presentation.driver.main.MainDriverScreen

fun NavGraphBuilder.driverGraph(
    navController: NavHostController,
){
    navigation(
        route = NavGraph.DRIVER,
        startDestination = BuyerRoute.MainNavigation.route
    ){
        composable (BuyerRoute.MainNavigation.route){
            MainDriverScreen(rootNavController = navController)
        }
    }
}