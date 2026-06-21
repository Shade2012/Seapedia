package com.example.seapedia.global.navigation.buyer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.seapedia.presentation.buyer.cart.CartBuyerScreen
import com.example.seapedia.presentation.buyer.home.HomeBuyerScreen
import com.example.seapedia.presentation.buyer.profile.ProfileBuyerScreen
import com.example.seapedia.presentation.buyer.product.ProductDetailBuyerScreen


@Composable
fun BuyerNavHost(
    buyerNavController: NavHostController,
    rootNavController: NavHostController,
    isGuest: Boolean,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = buyerNavController,
        startDestination = BuyerRoute.Home.route,
        modifier = modifier
    ) {

        composable(
            BuyerRoute.Home.route
        ) {
            HomeBuyerScreen(
                isGuest = isGuest,
                buyerNavController = buyerNavController,
                rootNavController = rootNavController
            )
        }
        composable(
            BuyerRoute.Profile.route
        ) {
            ProfileBuyerScreen(
                isGuest = isGuest,
                rootNavController = rootNavController
            )
        }
        composable(
            route = BuyerRoute.ProductDetail.route,
            arguments = listOf(
                navArgument("productId"){
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            ProductDetailBuyerScreen(
                navController = buyerNavController,
                isGuest = isGuest,
            )
        }
        composable(
            BuyerRoute.Cart.route
        ) {
            CartBuyerScreen()
        }
    }
}