package com.example.seapedia.global.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.seapedia.global.navigation.add_role.addRoleGraph
import com.example.seapedia.global.navigation.auth.authGraph
import com.example.seapedia.global.navigation.buyer.buyerGraph
import com.example.seapedia.global.navigation.review.reviewGraph
import com.example.seapedia.global.navigation.seller.sellerGraph
import com.example.seapedia.global.navigation.wallet_transactions.walletTransactionGraph


@Composable
fun RootNavGraph(
     navController: NavHostController,
     startDestination: String = NavGraph.AUTH,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(navController)
        buyerGraph(navController)
        sellerGraph(navController)
        reviewGraph(navController)
        walletTransactionGraph(navController)
        addRoleGraph(navController)
    }
}


