package com.example.seapedia.presentation.driver.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seapedia.global.navigation.driver.DriverNavHost
import com.example.seapedia.global.navigation.driver.DriverRoute
import com.example.seapedia.presentation.driver.main.widgets.BottomDriverBar

@Composable
fun MainDriverScreen(
    rootNavController: NavHostController,
) {
    val driverNavController = rememberNavController()

    val currentRoute = driverNavController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    Scaffold(
        bottomBar = {
            if (
                (currentRoute == DriverRoute.Home.route ||
                currentRoute == DriverRoute.JobHistory.route ||
                currentRoute == DriverRoute.Profile.route)
            ) {
                BottomDriverBar(
                    navController = driverNavController
                )
            }

        },
//        floatingActionButton = {
//            if(currentRoute == SellerRoute.ProductList.route){
//
//                CommonFloatingActionButton(
//                    onClick = {
//                        mainDriverViewModel.getValid()
//                        if(isValidStore){
//                            driverNavController.navigate(SellerRoute.ProductCreate.route)
//                        }
//                    },
//                    contentDescription = "Add Product"
//                )
//            }
//        }
    ) { padding ->
        DriverNavHost(
            driverNavController = driverNavController,
            rootNavController = rootNavController,
            modifier = Modifier.padding(padding),
        )
    }
}