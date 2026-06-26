package com.example.seapedia.presentation.seller.main.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.seapedia.R
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.ui.theme.White

@Composable
fun BottomSellerBar(
    navController: NavController,
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

            selected = currentRoute == SellerRoute.Home.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(SellerRoute.Home.route){
                    launchSingleTop=true
                    popUpTo(
                        SellerRoute.Home.route
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    "Home Seller"
                )
            },
            label = {
                Text("Home")
            }
        )
        NavigationBarItem(
            selected = currentRoute == SellerRoute.Order.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(SellerRoute.Order.route){
                    launchSingleTop=true
                    popUpTo(
                        SellerRoute.Order.route
                    )
                }
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.order_icon),
                    tint = White,
                    contentDescription = "Order Seller",
                )
            },
            label = {
                Text("Order")
            }
        )
        NavigationBarItem(
            selected = currentRoute == SellerRoute.ProductList.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(SellerRoute.ProductList.route){
                    launchSingleTop=true
                    popUpTo(
                        SellerRoute.ProductList.route
                    )
                }
            },
            icon = {
                Icon(
                    imageVector =  ImageVector.vectorResource(R.drawable.product_icon),
                    tint = White,
                    contentDescription =  "Product Seller"
                )
            },
            label = {
                Text("Product")
            }
        )
        NavigationBarItem(
            selected = currentRoute == SellerRoute.Store.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(SellerRoute.Store.route){
                    launchSingleTop=true
                    popUpTo(
                        SellerRoute.Store.route
                    )
                }
            },
            icon = {
                Icon(
                    imageVector =  ImageVector.vectorResource(R.drawable.store_icon),
                    tint = White,
                    contentDescription =  "Product Seller"
                )
            },
            label = {
                Text("Store")
            }
        )
        NavigationBarItem(
            selected = currentRoute == SellerRoute.Profile.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(SellerRoute.Profile.route){
                    launchSingleTop=true
                    popUpTo(
                        SellerRoute.Profile.route
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Default.Person,
                    "Profile Seller"
                )
            },
            label = {
                Text("Profile")
            }
        )
    }

}