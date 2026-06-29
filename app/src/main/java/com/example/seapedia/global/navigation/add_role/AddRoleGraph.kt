package com.example.seapedia.global.navigation.add_role

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.wallet_transactions.WalletTransactionsRoute
import com.example.seapedia.presentation.add_role.AddRoleScreen
import com.example.seapedia.presentation.wallet_transactions.WalletTransactionScreen


fun NavGraphBuilder.addRoleGraph(
    navController: NavHostController,
){
    navigation(
        route = NavGraph.ADD_ROLE,
        startDestination = AddRoleRoutes.AddRole.route
    ){
        composable (AddRoleRoutes.AddRole.route){
            AddRoleScreen(navController)
        }
    }
}