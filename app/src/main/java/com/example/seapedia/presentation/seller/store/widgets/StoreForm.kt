package com.example.seapedia.presentation.seller.store.widgets

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.seapedia.R
import com.example.seapedia.domain.entities.Region
import com.example.seapedia.global.utils.NormalSupportingText
import com.example.seapedia.global.utils.PhoneNumberSupportingText
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.presentation.seller.store.state.StoreFormState

@Composable
fun StoreForm(
    state: StoreFormState,

    onChangeName: (String) -> Unit,
    onChangePhoneNumber: (String) -> Unit,
    onChangeAddress: (String) -> Unit,
    onImageSelected: (Uri?) -> Unit,

    onProvinceSelected: (Region) -> Unit,
    onCitySelected: (Region) -> Unit,
    onDistrictSelected: (Region) -> Unit,
    onVillageSelected: (Region) -> Unit,

    ) {
    SingleImageCustom(
        image = state.image,
        imageUrl = state.imageUrl,
        onImageSelected = onImageSelected
    )
    TextFieldCustom(
        enabled = !state.isLoading,
        title = "Name",
        hint = "Input your store name",
        keyboardType = KeyboardType.Text,
        text = state.name,
        supportingText = NormalSupportingText,
        imeAction = ImeAction.Next,
        leadingIcon = {
            IconCustom(id = 0, icon = ImageVector.vectorResource(R.drawable.store_icon), contentDescription = "Store Name Icon")
        },
    ) {
        onChangeName(it)
    }

    TextFieldCustom(
        enabled = !state.isLoading,
        title = "Phone Number",
        hint = "Input your phone number",
        keyboardType = KeyboardType.Phone,
        text = state.phoneNumber,
        supportingText = PhoneNumberSupportingText,
        imeAction = ImeAction.Next,
        leadingIcon = {
            IconCustom(id = 0, icon = Icons.Default.Phone, contentDescription = "Phone Number Icon")
        },
    ) {
        onChangePhoneNumber(it)
    }

    RegionDropdown(
        state = state,
        onProvinceSelected =  onProvinceSelected,
        onCitySelected = onCitySelected,
        onDistrictSelected = onDistrictSelected,
        onVillageSelected =  onVillageSelected
    )

    TextFieldCustom(
        enabled = !state.isLoading,
        title = "Address Detail",
        hint = "Input your store address (e.g. Jl. Garuda A9)",
        keyboardType = KeyboardType.Text,
        minLines = 3,
        text = state.address,
        supportingText = NormalSupportingText,
        imeAction = ImeAction.Done,
    ) {
        onChangeAddress(it)
    }
}