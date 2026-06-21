package com.example.seapedia.global.navigation.review

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.presentation.buyer.main.MainBuyerScreen
import com.example.seapedia.presentation.review.all.ReviewAllScreen
import com.example.seapedia.presentation.review.create.ReviewCreateScreen

fun NavGraphBuilder.reviewGraph(
    navController: NavHostController,
){
    navigation(
        route = NavGraph.REVIEW,
        startDestination = ReviewRoutes.AllReview.route
    ){
        composable (ReviewRoutes.AllReview.route){
            ReviewAllScreen(
                navController = navController
            )
        }
        composable(ReviewRoutes.CreateReview.route){
            ReviewCreateScreen(navController = navController)
        }
    }
}