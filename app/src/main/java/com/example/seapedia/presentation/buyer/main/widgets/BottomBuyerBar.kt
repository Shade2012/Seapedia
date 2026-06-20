package com.example.seapedia.presentation.buyer.main.widgets

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.ui.theme.White

@Composable
fun BottomBuyerBar(
    navController: NavController,
    isGuest: Boolean
) {
    val currentRoute = navController.currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route
    val navBarColors = NavigationBarItemDefaults.colors(
        selectedIconColor = White,
        selectedTextColor = White,

        indicatorColor = MaterialTheme.colorScheme.tertiary,

        unselectedIconColor = White.copy(alpha = 0.7f),
        unselectedTextColor = White.copy(alpha = 0.7f)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        NavigationBarItem(
            selected = currentRoute == BuyerRoute.Home.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(BuyerRoute.Home.route){
                    launchSingleTop=true
                    popUpTo(
                        BuyerRoute.Home.route
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    "Home Buyer"
                )
            },
            label = {
                Text("Home")
            }
        )
        if(!isGuest){
            NavigationBarItem(
                selected = currentRoute == BuyerRoute.Cart.route,
                colors = navBarColors,
                onClick = {
                    navController.navigate(BuyerRoute.Cart.route){
                        launchSingleTop=true
                        popUpTo(
                            BuyerRoute.Cart.route
                        )
                    }
                },
                icon = {
                    Icon(
                        Icons.Default.ShoppingCart,
                        "Cart Buyer"
                    )
                },
                label = {
                    Text("Cart")
                }
            )
        }
        NavigationBarItem(
            selected = currentRoute == BuyerRoute.Profile.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(BuyerRoute.Profile.route){
                    launchSingleTop=true
                    popUpTo(
                        BuyerRoute.Profile.route
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Default.Person,
                    "Profile Buyer"
                )
            },
            label = {
                Text("Profile")
            }
        )
    }

}