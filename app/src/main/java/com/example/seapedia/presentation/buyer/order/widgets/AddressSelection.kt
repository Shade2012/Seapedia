package com.example.seapedia.presentation.buyer.order.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.presentation.buyer.address.all.BuyerAddressViewModel
import com.example.seapedia.presentation.buyer.address.all.widgets.AddressCard
import com.example.seapedia.presentation.buyer.address.all.widgets.addressSection
import com.example.seapedia.presentation.buyer.widgets.BottomSheetCard
import com.example.seapedia.presentation.buyer.widgets.BottomSheetShimmer
import com.example.seapedia.presentation.common.ConfirmationDialogCustom
import com.example.seapedia.ui.theme.Dimens


@Composable
fun AddressSelection(
    isVisible: Boolean,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    addresses: List<Address>,
    buyerNavController: NavController,
    onClick : (Address) -> Unit,
) {
    BottomSheetCard(
        visible = isVisible,
        onDismiss = onDismiss
    ) {
        if(!isLoading){
            LazyColumn(
                modifier = Modifier.padding(horizontal = Dimens.InnerPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
            ) {
                addressSection(
                    addresses = addresses,
                    onClick = {
                        onClick(it)
                        onDismiss()
                    },
                    onClickUpdate = {
                        buyerNavController.navigate(BuyerRoute.BuyerUpdateAddress.createRoute(addressId = it.id))
                    },
                    enableDelete = false,
                    onClickDelete = {}
                )
            }
        }else{
            BottomSheetShimmer()
        }
    }

}
