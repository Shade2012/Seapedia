package com.example.seapedia.global.navigation.driver

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.presentation.buyer.address.all.BuyerAddressScreen
import com.example.seapedia.presentation.buyer.address.create.BuyerAddressCreateScreen
import com.example.seapedia.presentation.buyer.address.update.BuyerAddressUpdateScreen
import com.example.seapedia.presentation.buyer.cart.CartBuyerScreen
import com.example.seapedia.presentation.buyer.cart.add.AddCartItemScreen
import com.example.seapedia.presentation.buyer.home.HomeBuyerScreen
import com.example.seapedia.presentation.buyer.order.OrderCheckoutScreen
import com.example.seapedia.presentation.buyer.product.ProductDetailBuyerScreen
import com.example.seapedia.presentation.buyer.profile.ProfileBuyerScreen
import com.example.seapedia.presentation.buyer.profile.phone.UpdatePhoneNumberScreen
import com.example.seapedia.presentation.buyer.topup.TopUpScreen
import com.example.seapedia.presentation.driver.history.JobHistoryScreen
import com.example.seapedia.presentation.driver.home.HomeDriverScreen
import com.example.seapedia.presentation.driver.job_detail.JobDetailScreen
import com.example.seapedia.presentation.driver.profile.ProfileDriverScreen

@Composable
fun DriverNavHost(
    driverNavController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = driverNavController,
        startDestination = DriverRoute.Home.route,
        modifier = modifier
    ) {
        composable (
            route = DriverRoute.Home.route
        ){
            HomeDriverScreen(
                rootNavController,
                driverNavController
            )
        }

        composable (
            route = DriverRoute.JobHistory.route
        ){
            JobHistoryScreen(
                driverNavController
            )
        }

        composable (
            route = DriverRoute.JobDetail.route,
            arguments = listOf(
                navArgument("jobId"){
                    type = NavType.IntType
                }
            )
        ){
            backStackEntry ->
            JobDetailScreen(
                navController = driverNavController,
            )
        }

        composable (
            route = DriverRoute.Profile.route
        ){
            ProfileDriverScreen(
                rootNavController
            )
        }
    }
}