package com.example.seapedia.presentation.buyer.profile.phone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.R
import com.example.seapedia.global.utils.PhoneNumberSupportingText
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.ui.theme.Dimens


@Composable
fun UpdatePhoneNumberScreen(
    buyerNavController: NavController,
    modifier: Modifier = Modifier,
    viewModel: UpdatePhoneNumberViewModel = hiltViewModel<UpdatePhoneNumberViewModel>(),

) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.navigateToProfile.collect {
            buyerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_profile",true)
            buyerNavController.popBackStack()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.SpacePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageCustom(
            vector = ImageVector.vectorResource(R.drawable.phone_number_illus),
            modifier = Modifier.size(200.dp),
            contentDescription = "//    <a href=\"https://storyset.com/technology\">Technology illustrations by Storyset</a>"
        )
        Spacer(Modifier.padding(Dimens.SpacePadding))
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
            onTextChange = viewModel::onPhoneNumberChange
        )
        Spacer(Modifier.padding(Dimens.SpacePadding))
        ButtonCustom(
            enabled = !state.isLoading,
            isNotLoading = !state.isLoading,
            title = "Update Phone Number",
            onClick = viewModel::updatePhoneNumber
        )
    }
}