package com.example.seapedia.presentation.buyer.topup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.R
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.global.utils.RupiahSupportingText
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun TopUpScreen(
    buyerNavController: NavController,
    viewModel: TopUpViewModel = hiltViewModel<TopUpViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.isSuccess.collect {
            buyerNavController.popBackStack()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(Dimens.InnerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageCustom(
            contentDescription = "<a href=\"https://storyset.com/app\">App illustrations by Storyset</a>",
            vector = ImageVector.vectorResource(R.drawable.topup)
        )
        Spacer(Modifier.height(Dimens.SpacePadding))
        TextFieldCustom(
            enabled = !state.isLoading,
            title = "Amount",
            hint = "Input your amount",
            keyboardType = KeyboardType.Number,
            text = Formatting().formatRupiah(state.amount),
            supportingText = RupiahSupportingText,
            imeAction = ImeAction.Done,
        ) {
            value ->
            viewModel.onAmountChange(value.filter { it.isDigit() })
        }
        Spacer(Modifier.height(Dimens.SpacePadding))
        ButtonCustom(
            enabled = !state.isLoading && (state.amount.toIntOrNull() ?: 0) > 0,
            isNotLoading = !state.isLoading,
            title = "Top Up",
            onClick =  viewModel::onTopUp
        )
    }
}