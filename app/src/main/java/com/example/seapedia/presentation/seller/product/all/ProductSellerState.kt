package com.example.seapedia.presentation.seller.product.all

import com.example.seapedia.domain.entities.SellerProduct
import com.example.seapedia.global.utils.CommonState

data class ProductSellerState(
    val data: CommonState<SellerProduct> = CommonState.Loading(),
    val searchName:String = "",
    val isRefreshing : Boolean = false
)