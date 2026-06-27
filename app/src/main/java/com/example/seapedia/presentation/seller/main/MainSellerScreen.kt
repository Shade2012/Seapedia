package com.example.seapedia.presentation.seller.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seapedia.global.navigation.seller.SellerNavHost
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.presentation.common.AlertDialogCustom
import com.example.seapedia.presentation.common.CommonFloatingActionButton
import com.example.seapedia.presentation.seller.main.widgets.BottomSellerBar

@Composable
fun MainSellerScreen(
    rootNavController: NavHostController,
    mainSellerViewModel: MainSellerViewModel = hiltViewModel<MainSellerViewModel>()
) {
    val sellerNavController = rememberNavController()
    val showDialog by mainSellerViewModel.showInvalidStoreDialog.collectAsState()
    val isValidStore by mainSellerViewModel.validState.collectAsState()

    if (showDialog) {
        AlertDialogCustom(
            dialogTitle = "Store Required",
            dialogText = "Please create a store first.",
            icon = Icons.Default.Warning,
            onDismissRequest = {
                mainSellerViewModel.dismissDialog()
            },
            onConfirmation = {
                mainSellerViewModel.dismissDialog()
            }
        )
    }
    val currentRoute = sellerNavController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    Scaffold(
        bottomBar = {
            if (
                currentRoute == SellerRoute.Home.route ||
                currentRoute == SellerRoute.Order.route ||
                currentRoute == SellerRoute.ProductList.route ||
                currentRoute == SellerRoute.Store.route ||
                currentRoute == SellerRoute.Profile.route
            ) {
                BottomSellerBar(
                    navController = sellerNavController
                )
            }

        },
        floatingActionButton = {
            if(currentRoute == SellerRoute.ProductList.route){

                CommonFloatingActionButton(
                    onClick = {
                        mainSellerViewModel.getValid()
                        if(isValidStore){
                            sellerNavController.navigate(SellerRoute.ProductCreate.route)
                        }
                    },
                    contentDescription = "Add Product"
                )
            }
        }
    ) { padding ->
        SellerNavHost(
            sellerNavController = sellerNavController,
            rootNavController = rootNavController,
            modifier = Modifier.padding(padding),
            mainSellerViewModel = mainSellerViewModel
        )
    }
}