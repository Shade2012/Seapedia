package com.example.seapedia.global.navigation.wallet_transactions

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.seapedia.global.navigation.NavGraph

import com.example.seapedia.presentation.seller.main.MainSellerScreen
import com.example.seapedia.presentation.wallet_transactions.WalletTransactionScreen

fun NavGraphBuilder.walletTransactionGraph(
    navController: NavHostController,
){
    navigation(
        route = NavGraph.WALLET_TRANSACTIONS,
        startDestination = WalletTransactionsRoute.WalletTransactionAll.route
    ){
        composable (WalletTransactionsRoute.WalletTransactionAll.route){
            WalletTransactionScreen(navController)
        }
    }
}