package com.example.seapedia.presentation.seller.product.all

import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.CommonState

data class ProductSellerState(
    val products: CommonState<List<ProductEntity>> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)