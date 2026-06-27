package com.example.seapedia.presentation.seller.product.detail

import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.CommonState

data class ProductSellerDetailState(
    val product: CommonState<ProductEntity> = CommonState.Loading(),
)