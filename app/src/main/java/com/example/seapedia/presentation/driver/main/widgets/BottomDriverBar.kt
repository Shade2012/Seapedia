package com.example.seapedia.presentation.driver.main.widgets

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
import com.example.seapedia.global.navigation.driver.DriverRoute
import com.example.seapedia.ui.theme.White

@Composable
fun BottomDriverBar(
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
            selected = currentRoute == DriverRoute.Home.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(DriverRoute.Home.route){
                    launchSingleTop=true
                    popUpTo(
                        DriverRoute.Home.route
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    "Home Driver"
                )
            },
            label = {
                Text("Home")
            }
        )
        NavigationBarItem(
            selected = currentRoute == DriverRoute.JobHistory.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(DriverRoute.JobHistory.route){
                    launchSingleTop=true
                    popUpTo(
                        DriverRoute.JobHistory.route
                    )
                }
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.order_icon),
                    tint = White,
                    contentDescription = "Job History Driver",
                )
            },
            label = {
                Text("History")
            }
        )
        NavigationBarItem(
            selected = currentRoute == DriverRoute.Profile.route,
            colors = navBarColors,
            onClick = {
                navController.navigate(DriverRoute.Profile.route){
                    launchSingleTop=true
                    popUpTo(
                        DriverRoute.Profile.route
                    )
                }
            },
            icon = {
                Icon(
                    Icons.Default.Person,
                    "Profile Driver"
                )
            },
            label = {
                Text("Profile")
            }
        )
    }

}