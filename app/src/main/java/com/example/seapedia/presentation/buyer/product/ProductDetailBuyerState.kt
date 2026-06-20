package com.example.seapedia.presentation.buyer.product

import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.CommonState

data class ProductDetailBuyerState(
    val selectedImage: String? = null,
    val product: CommonState<ProductEntity> = CommonState.Loading()
)