package com.example.seapedia.presentation.seller.store.all

import com.example.seapedia.domain.entities.Store
import com.example.seapedia.global.utils.CommonState


data class StoreSellerState(
    val store: CommonState<Store?> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)