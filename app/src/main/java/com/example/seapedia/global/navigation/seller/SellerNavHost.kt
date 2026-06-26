package com.example.seapedia.global.navigation.seller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.seapedia.presentation.seller.home.HomeSellerScreen
import com.example.seapedia.presentation.seller.main.MainSellerViewModel
import com.example.seapedia.presentation.seller.order.OrderSellerScreen
import com.example.seapedia.presentation.seller.product.all.ProductSellerScreen
import com.example.seapedia.presentation.seller.product.create.ProductSellerCreateScreen
import com.example.seapedia.presentation.seller.profile.ProfileSellerScreen
import com.example.seapedia.presentation.seller.store.all.StoreSellerScreen
import com.example.seapedia.presentation.seller.store.create.StoreSellerCreateScreen
import com.example.seapedia.presentation.seller.store.update.StoreUpdateScreen


@Composable
fun SellerNavHost(
    sellerNavController: NavHostController,
    rootNavController: NavHostController,
    mainSellerViewModel: MainSellerViewModel,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = sellerNavController,
        startDestination = SellerRoute.Home.route,
        modifier = modifier
    ) {

        composable(
            SellerRoute.Home.route
        ) {
            HomeSellerScreen(
                sellerNavController
            )
        }

        composable(
            SellerRoute.Order.route
        ) {
            OrderSellerScreen(
                sellerNavController
            )
        }

        composable(
            SellerRoute.ProductList.route
        ) {
            ProductSellerScreen(
                sellerNavController
            )
        }

        composable(
            SellerRoute.ProductCreate.route
        ) {
            ProductSellerCreateScreen(
                sellerNavController
            )
        }

//        Store
        composable(
            SellerRoute.Store.route
        ) {
            StoreSellerScreen(
                sellerNavController,
                mainSellerViewModel
            )
        }

        composable(
            SellerRoute.StoreCreate.route
        ) {
            StoreSellerCreateScreen(
                sellerNavController
            )
        }
        composable(
            SellerRoute.StoreUpdate.route
        ){
            StoreUpdateScreen(
                sellerNavController
            )
        }

//        Profile
        composable(
            SellerRoute.Profile.route
        ) {
            ProfileSellerScreen(
                sellerNavController,
                rootNavController
            )
        }
    }
}