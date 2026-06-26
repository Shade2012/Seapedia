package com.example.seapedia.presentation.seller.store.create

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.seapedia.R
import com.example.seapedia.global.utils.NormalSupportingText
import com.example.seapedia.global.utils.PhoneNumberSupportingText
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.seller.store.widgets.RegionDropdown
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
            onNameChange = viewModel::onChangeName,
            onPhoneChange = viewModel::onChangePhoneNumber,
            onAddressChange = viewModel::onChangeAddress,
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



