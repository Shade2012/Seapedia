package com.example.seapedia.presentation.buyer.cart.add

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.address.widgets.AddressForm
import com.example.seapedia.presentation.buyer.cart.widgets.CartItemForm
import com.example.seapedia.presentation.buyer.widgets.BuyerBody
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom

@Composable
fun AddCartItemScreen(
    buyerNavController: NavController,
    viewModel: AddScreenViewModel = hiltViewModel<AddScreenViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        viewModel.navigateBackStack.collect {
            buyerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_product",true)
            buyerNavController.popBackStack()
        }
    }
    BuyerBody(scrollState = scrollState, enableRefresh = false) {
        TopAppBarCustom(onBackClick = {
            buyerNavController.popBackStack()
        },title = "Add Cart")
        when(val result = state.product){
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = result.message)
            }
            is CommonState.Loading<*> -> {
                CircularProgressIndicator()
            }
            is CommonState.Success -> {
                CartItemForm(
                    product = result.data,
                    state = state.state,
                    onIncreaseQuantity = viewModel::increaseQuantity,
                    onDecreaseQuantity = viewModel::decreaseQuantity,
                    onSingleTypeSelected = viewModel::selectSingleType,
                    onMultipleTypeToggled = viewModel::toggleMultipleType
                )

                ButtonCustom(
                    title = "Add to Cart",
                    enabled = state.state.canSubmit && !state.isLoading,
                    isNotLoading = !state.isLoading,
                    onClick = viewModel::submit
                )
            }
        }
    }


}
