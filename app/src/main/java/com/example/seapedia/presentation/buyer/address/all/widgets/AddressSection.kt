package com.example.seapedia.presentation.buyer.address.all.widgets

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seapedia.domain.entities.Address


fun LazyListScope.addressSection(
    addresses: List<Address>,
    onClickUpdate: (Address) -> Unit,
    onClickDelete: (Address) -> Unit,
){
    items(
        addresses,
        key = {it.id}
    ){
        address ->
        AddressCard(
            address = address,
            onClickDelete = onClickDelete,
            onClickUpdate = onClickUpdate
        )
    }
}