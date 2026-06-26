package com.example.seapedia.presentation.seller.store.update

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.seller.store.create.StoreSellerCreateViewModel
import com.example.seapedia.presentation.seller.store.widgets.StoreForm
import com.example.seapedia.presentation.seller.widgets.SellerBody

@Composable
fun StoreUpdateScreen(
    sellerNavController: NavController,
    viewModel: StoreUpdateViewModel = hiltViewModel<StoreUpdateViewModel>()
) {
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.navigateToStoreAll.collect {
            sellerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_store",true)
            sellerNavController.popBackStack()
        }
    }
    SellerBody(scrollState, enableRefresh = false) {
        TopAppBarCustom(onBackClick = {
            sellerNavController.popBackStack()
        },title = "Update Store")
        StoreForm(
            state = state,
            onProvinceSelected = viewModel::onChangeProvince,
            onCitySelected = viewModel::onChangeCity,
            onDistrictSelected = viewModel::onChangeDistrict,
            onVillageSelected = viewModel::onChangeVillage,
            onNameChange = viewModel::onChangeName,
            onPhoneChange = viewModel::onChangePhoneNumber,
            onAddressChange = viewModel::onChangeAddress,
            onImageSelected = viewModel::onImageSelected,
        )

        ButtonCustom(
            enabled = !state.nameError and !state.phoneNumberError and !state.addressError and !state.provinceError and
                    !state.cityError and !state.districtError and !state.villageError,
            isNotLoading = !state.loading,
            title = "Update Store",
            onClick = viewModel::updateStore
        )
    }
}