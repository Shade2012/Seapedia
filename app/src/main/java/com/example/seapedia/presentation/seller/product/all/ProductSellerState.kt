package com.example.seapedia.presentation.seller.product.all

import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.entities.SellerProductEntity
import com.example.seapedia.global.utils.CommonState

data class ProductSellerState(
    val data: CommonState<SellerProductEntity> = CommonState.Loading(),
    val searchName:String = "",
    val isRefreshing : Boolean = false
)