package com.example.seapedia.presentation.buyer.address.widgets

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.seapedia.R
import com.example.seapedia.domain.entities.Region
import com.example.seapedia.global.utils.NormalSupportingText
import com.example.seapedia.global.utils.PhoneNumberSupportingText
import com.example.seapedia.presentation.buyer.address.state.AddressFormState
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.presentation.common.RegionDropdown
import com.example.seapedia.presentation.common.TextFieldCustom

@Composable
fun AddressForm(
    state: AddressFormState,

    onChangeName: (String) -> Unit,
    onChangeReceiverName: (String) -> Unit,
    onChangeAddress: (String) -> Unit,

    onProvinceSelected: (Region) -> Unit,
    onCitySelected: (Region) -> Unit,
    onDistrictSelected: (Region) -> Unit,
    onVillageSelected: (Region) -> Unit,
) {
    TextFieldCustom(
        enabled = !state.isLoading,
        title = "Name",
        hint = "Name Address (e.g. Home, Hotal)",
        keyboardType = KeyboardType.Text,
        text = state.name,
        supportingText = NormalSupportingText,
        imeAction = ImeAction.Next,
        leadingIcon = {
            IconCustom(id = 0, icon = ImageVector.vectorResource(R.drawable.store_icon), contentDescription = "Address Name Icon")
        },
    ) {
        onChangeName(it)
    }

    TextFieldCustom(
        enabled = !state.isLoading,
            title = "Receiver Name",
        hint = "Receiver Name of the address",
        keyboardType = KeyboardType.Text,
        text = state.receiverName,
        supportingText = NormalSupportingText,
        imeAction = ImeAction.Next,
        leadingIcon = {
            IconCustom(id = 0, icon = Icons.Default.Person, contentDescription = "Receiver Icon")
        },
    ) {
        onChangeReceiverName(it)
    }

    RegionDropdown(
        onProvinceSelected = onProvinceSelected,
        onCitySelected = onCitySelected,
        onDistrictSelected = onDistrictSelected,
        onVillageSelected = onVillageSelected,
        province = state.province,
        provinceLoading = state.provinceLoading,
        city = state.city,
        cityLoading = state.cityLoading,
        district = state.district,
        districtLoading = state.districtLoading,
        village = state.village,
        villageLoading = state.villageLoading,
        isLoading = state.isLoading,
        provinceList = state.provinceList,
        cityList = state.cityList,
        districtList = state.districtList,
        villageList = state.villageList,
    )

    TextFieldCustom(
        enabled = !state.isLoading,
        title = "Address Detail",
        hint = "Input your store address (e.g. Jl. Garuda A9)",
        keyboardType = KeyboardType.Text,
        minLines = 3,
        text = state.addressDetail,
        supportingText = NormalSupportingText,
        imeAction = ImeAction.Done,
    ) {
        onChangeAddress(it)
    }
}