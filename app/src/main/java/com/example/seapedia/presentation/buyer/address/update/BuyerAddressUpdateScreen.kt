package com.example.seapedia.presentation.buyer.address.update

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.presentation.buyer.address.widgets.AddressForm
import com.example.seapedia.presentation.buyer.widgets.BuyerBody
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom

@Composable
fun BuyerAddressUpdateScreen(
    buyerNavController: NavController,
    viewModel: BuyerAddressUpdateViewModel = hiltViewModel<BuyerAddressUpdateViewModel>()
) {
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.navigateToAddressAll.collect {
            buyerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_address",true)
            buyerNavController.popBackStack()
        }
    }
    BuyerBody(scrollState = scrollState, enableRefresh = false) {
        TopAppBarCustom(onBackClick = {
            buyerNavController.popBackStack()
        },title = "Update Address")
        AddressForm(
            state = state,
            onProvinceSelected = viewModel::onChangeProvince,
            onCitySelected = viewModel::onChangeCity,
            onDistrictSelected = viewModel::onChangeDistrict,
            onVillageSelected = viewModel::onChangeVillage,
            onChangeName = viewModel::onChangeName,
            onChangeAddress = viewModel::onChangeAddressDetail,
            onChangeReceiverName = viewModel::onChangeReceiverName,
        )
        ButtonCustom(
            enabled = !state.nameError and !state.receiverNameError and !state.addressDetailError and !state.provinceError and
                    !state.cityError and !state.districtError and !state.villageError,
            isNotLoading = !state.isLoading,
            title = "Update Addresss",
            onClick = viewModel::updateAddress
        )
    }
}