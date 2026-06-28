package com.example.seapedia.presentation.buyer.address.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.address.all.widgets.addressSection
import com.example.seapedia.presentation.buyer.address.shimmer.BuyerAddressShimmer
import com.example.seapedia.presentation.buyer.widgets.BuyerLazyBody
import com.example.seapedia.presentation.common.ConfirmationDialogCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom

@Composable
fun BuyerAddressScreen(
    buyerNavController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BuyerAddressViewModel = hiltViewModel<BuyerAddressViewModel>(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val refresh = buyerNavController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_address",false)
    LaunchedEffect(Unit) {
        refresh?.collect {
                shouldRefresh ->
            if(shouldRefresh){
                viewModel.onInit()
                buyerNavController.currentBackStackEntry?.savedStateHandle?.set("refresh_store",false)
            }
        }
    }
    var addressToDelete by rememberSaveable {
        mutableStateOf<Address?>(null)
    }
    BuyerLazyBody(
        onRefresh = viewModel::onRefresh,
        isRefreshing = state.isRefreshing,
    ) {
        item {
            TopAppBarCustom(
                onBackClick = {
                    buyerNavController.popBackStack()
                },
                title = "Addresses"
            )
        }
        when(val result = state.address){
            is CommonState.Error<*> -> {
                item {
                    FailedCommonCustom(text = result.message)
                }
            }
            is CommonState.Loading -> {
                item {
                    BuyerAddressShimmer()
                }
            }
            is CommonState.Success -> {
                addressSection(
                    addresses = result.data,
                    onClickUpdate = {
                        buyerNavController.navigate(BuyerRoute.BuyerUpdateAddress.createRoute(addressId = it.id))
                    },
                    onClickDelete = {
                        addressToDelete = it
                    }
                )
            }
        }
    }
    ConfirmationDialogCustom(
        visible = addressToDelete != null,
        title = "Delete Address",
        message = "Are you sure want to delete this address ? this action cannot be undone.",
        confirmText = "Delete",
        onDismiss = {
            addressToDelete = null
        },
        onConfirm = {
            addressToDelete?.let {
                viewModel.deleteAddress(it.id)
            }
            addressToDelete = null
        }
    )
}