package com.example.seapedia.presentation.buyer.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.seapedia.global.navigation.buyer.BuyerNavHost
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.buyer.main.widgets.BottomBuyerBar

@Composable
fun MainBuyerScreen(
    rootNavController: NavHostController,
    mainBuyerViewModel: MainBuyerViewModel = hiltViewModel<MainBuyerViewModel>()
) {
    val buyerNavController = rememberNavController()
    val session by mainBuyerViewModel.state.collectAsStateWithLifecycle()
    val isGuest = session.role != UserRole.Buyer


    Scaffold(
        bottomBar = {
            BottomBuyerBar(buyerNavController,isGuest)
        }
    ) {
        padding ->
        BuyerNavHost(
            buyerNavController = buyerNavController,
            rootNavController = rootNavController,
            isGuest = isGuest,
            modifier = Modifier.padding(padding)
        )
    }
}