package com.example.seapedia.presentation.seller.store.all

import com.example.seapedia.domain.entities.StoreEntity
import com.example.seapedia.global.utils.CommonState


data class StoreSellerState(
    val store: CommonState<StoreEntity?> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)