package com.example.seapedia.presentation.seller.store.create

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.seller.store.widgets.StoreForm
import com.example.seapedia.presentation.seller.widgets.SellerBody

@Composable
fun StoreSellerCreateScreen(
    sellerNavController : NavController,
    viewModel: StoreSellerCreateViewModel = hiltViewModel<StoreSellerCreateViewModel>()
) {
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.navigateToStoreAll.collect {
            sellerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_store",true)
            sellerNavController.popBackStack()
        }
    }
    SellerBody(scrollState,enableRefresh = false) {
        TopAppBarCustom(onBackClick = {
            sellerNavController.popBackStack()
        },title = "Create Store")
        StoreForm(
            state = state,
            onProvinceSelected = viewModel::onChangeProvince,
            onCitySelected = viewModel::onChangeCity,
            onDistrictSelected = viewModel::onChangeDistrict,
            onVillageSelected = viewModel::onChangeVillage,
            onChangeName = viewModel::onChangeName,
            onChangePhoneNumber = viewModel::onChangePhoneNumber,
            onChangeAddress = viewModel::onChangeAddress,
            onImageSelected = viewModel::onImageSelected,
        )
        ButtonCustom(
            enabled = !state.nameError and !state.phoneNumberError and !state.addressError and !state.provinceError and
                    !state.cityError and !state.districtError and !state.villageError && state.image != null,
            isNotLoading = !state.loading,
            title = "Create Store",
            onClick = viewModel::createStore
        )
    }
}



