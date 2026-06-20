package com.example.seapedia.global.navigation.buyer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.seapedia.presentation.buyer.cart.CartBuyerScreen
import com.example.seapedia.presentation.buyer.home.HomeBuyerScreen
import com.example.seapedia.presentation.buyer.profile.ProfileBuyerScreen


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
                buyerNavController = buyerNavController
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
            BuyerRoute.Cart.route
        ) {
            CartBuyerScreen()
        }
    }
}