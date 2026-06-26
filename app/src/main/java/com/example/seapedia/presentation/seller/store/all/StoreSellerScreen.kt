package com.example.seapedia.presentation.seller.store.all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.seller.main.MainSellerViewModel
import com.example.seapedia.presentation.seller.store.all.shimmer.StoreShimmer
import com.example.seapedia.presentation.seller.store.all.widgets.StoreCard
import com.example.seapedia.presentation.seller.widgets.SellerBody
import com.example.seapedia.ui.theme.Dimens
import okhttp3.Route

@Composable
fun StoreSellerScreen(
    sellerNavController: NavController,
    mainViewModel : MainSellerViewModel,
    modifier: Modifier = Modifier,
    storeSellerViewModel: StoreSellerViewModel = hiltViewModel<StoreSellerViewModel>()
) {
    val scrollState = rememberScrollState()
    val refresh = sellerNavController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_store",false)
    val state = storeSellerViewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        refresh?.collect {
                shouldRefresh ->
            if(shouldRefresh){
                storeSellerViewModel.getStore()
                sellerNavController.currentBackStackEntry?.savedStateHandle?.set("refresh_store",false)
            }
        }
    }
    SellerBody(
        scrollState,
        isRefreshing = state.isRefreshing,
        onRefresh = storeSellerViewModel::refreshStore
    ) {
        StoreContent(
            state = state,
            sellerNavController = sellerNavController
        )
    }
}

@Composable
fun StoreContent(
    state: StoreSellerState,
    sellerNavController: NavController,
    modifier: Modifier = Modifier) {
    when(val result = state.store){
        is CommonState.Error<*> -> {
            FailedCommonCustom(text = result.message)
        }
        is CommonState.Loading<*> -> {
            StoreShimmer()
        }
        is CommonState.Success -> {
            if(result.data == null){
                FailedCommonCustom(text = "Store Not Found, Please Create It First")
                ButtonCustom(
                    enabled = true,
                    title = "Create Store",
                    isNotLoading = true
                ) {
                    sellerNavController.navigate(SellerRoute.StoreCreate.route)
                }

            }else{
                StoreCard(store = result.data,
                    onClick = {
                        sellerNavController.navigate(SellerRoute.StoreUpdate.route)
                    })
            }
        }
    }
}