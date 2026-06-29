package com.example.seapedia.presentation.buyer.product

import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.utils.CommonState

data class ProductDetailBuyerState(
    val selectedImage: String? = null,
    val bottomSheetLoading: Boolean? = null,
    val product: CommonState<Product> = CommonState.Loading()
)