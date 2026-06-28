package com.example.seapedia.global.navigation.wallet_transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.seapedia.presentation.seller.home.HomeSellerScreen
import com.example.seapedia.presentation.seller.main.MainSellerViewModel
import com.example.seapedia.presentation.seller.order.all.OrderSellerScreen
import com.example.seapedia.presentation.seller.order.detail.OrderSellerDetailScreen
import com.example.seapedia.presentation.seller.product.all.ProductSellerScreen
import com.example.seapedia.presentation.seller.product.create.ProductSellerCreateScreen
import com.example.seapedia.presentation.seller.product.detail.ProductSellerDetailScreen
import com.example.seapedia.presentation.seller.product.update.ProductSellerUpdateScreen
import com.example.seapedia.presentation.seller.product.update.image.ProductSellerUpdateImage
import com.example.seapedia.presentation.seller.profile.ProfileSellerScreen
import com.example.seapedia.presentation.seller.store.all.StoreSellerScreen
import com.example.seapedia.presentation.seller.store.create.StoreSellerCreateScreen
import com.example.seapedia.presentation.seller.store.update.StoreUpdateScreen


//@Composable
//fun SellerNavHost(
//    sellerNavController: NavHostController,
//    rootNavController: NavHostController,
//    mainSellerViewModel: MainSellerViewModel,
//    modifier: Modifier = Modifier
//) {
//
//    NavHost(
//        navController = sellerNavController,
//        startDestination = SellerRoute.Home.route,
//        modifier = modifier
//    ) {
//
//        composable(
//            SellerRoute.Home.route
//        ) {
//            HomeSellerScreen(
//                sellerNavController
//            )
//        }
//
//        composable(
//            SellerRoute.Order.route
//        ) {
//            OrderSellerScreen(
//                sellerNavController
//            )
//        }
//
//        composable(
//            route = SellerRoute.OrderDetail.route,
//            arguments = listOf(
//                navArgument("orderId"){
//                    type = NavType.IntType
//                }
//            )
//        ) { backStackEntry ->
//            OrderSellerDetailScreen(
//                sellerNavController
//            )
//        }
//
//
//        composable(
//            SellerRoute.ProductList.route
//        ) {
//            ProductSellerScreen(
//                sellerNavController
//            )
//        }
//
//        composable(
//            route = SellerRoute.ProductDetail.route,
//            arguments = listOf(
//                navArgument("productId"){
//                    type = NavType.IntType
//                }
//            )
//        ) { backStackEntry ->
//            ProductSellerDetailScreen(
//                sellerNavController = sellerNavController,
//            )
//        }
//
//        composable(
//            SellerRoute.ProductCreate.route
//        ) {
//            ProductSellerCreateScreen(
//                sellerNavController
//            )
//        }
//
//        composable(
//            route = SellerRoute.ProductUpdate.route,
//            arguments = listOf(
//                navArgument("productId"){
//                    type = NavType.IntType
//                }
//            )
//        ) { backStackEntry ->
//            ProductSellerUpdateScreen(
//                sellerNavController
//            )
//        }
//        composable(
//            route = SellerRoute.ProductImageUpdate.route,
//            arguments = listOf(
//                navArgument("productId"){
//                    type = NavType.IntType
//                }
//            )
//        ) { backStackEntry ->
//            ProductSellerUpdateImage(
//                sellerNavController
//            )
//        }
//
//
////        Store
//        composable(
//            SellerRoute.Store.route
//        ) {
//            StoreSellerScreen(
//                sellerNavController,
//                mainSellerViewModel
//            )
//        }
//
//        composable(
//            SellerRoute.StoreCreate.route
//        ) {
//            StoreSellerCreateScreen(
//                sellerNavController
//            )
//        }
//        composable(
//            SellerRoute.StoreUpdate.route
//        ){
//            StoreUpdateScreen(
//                sellerNavController
//            )
//        }
//
//
////        Profile
//        composable(
//            SellerRoute.Profile.route
//        ) {
//            ProfileSellerScreen(
//                sellerNavController,
//                rootNavController
//            )
//        }
//    }
//}