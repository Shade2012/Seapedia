package com.example.seapedia.presentation.buyer.address.all

import com.example.seapedia.domain.entities.Address
import com.example.seapedia.global.utils.CommonState

data class BuyerAddressState(
    val address : CommonState<List<Address>> = CommonState.Loading(),
    val isRefreshing: Boolean = false
)