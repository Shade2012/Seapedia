package com.example.seapedia.global.navigation.seller

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.seapedia.global.navigation.NavGraph

import com.example.seapedia.presentation.buyer.main.MainBuyerScreen
import com.example.seapedia.presentation.seller.main.MainSellerScreen

fun NavGraphBuilder.sellerGraph(
    navController: NavHostController,
){
    navigation(
        route = NavGraph.SELLER,
        startDestination = SellerRoute.MainNavigation.route
    ){
        composable (SellerRoute.MainNavigation.route){
            MainSellerScreen(rootNavController = navController)
        }
    }
}