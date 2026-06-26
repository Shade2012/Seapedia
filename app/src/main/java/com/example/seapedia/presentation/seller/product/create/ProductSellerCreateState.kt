package com.example.seapedia.presentation.seller.product.create

import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.CommonState

data class ProductSellerCreateState(
    val products: CommonState<List<ProductEntity>> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)