package com.example.seapedia.presentation.seller.product.all

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.common.EmptyCommonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.seller.widgets.SellerBody

@Composable
fun ProductSellerScreen(
    sellerNavController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProductSellerViewModel = hiltViewModel<ProductSellerViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    //    LaunchedEffect(Unit) {
    //        profileBuyerViewModel.navigateToBuyer.collect {
    //            rootNavController.navigate(NavGraph.AUTH){
    //                popUpTo(0)
    //            }
    //        }
    //    }
    SellerBody(scrollState){
        when(val result = state.products){
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = result.message)
            }
            is CommonState.Loading<*> -> {
                CircularProgressIndicator()
            }
            is CommonState.Success -> {
                if(result.data.isEmpty()){
                    EmptyCommonCustom(text = "No Product yet")
                }
            }
        }
    }
}