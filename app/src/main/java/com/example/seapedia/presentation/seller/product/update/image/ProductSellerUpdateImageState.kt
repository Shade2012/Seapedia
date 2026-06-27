package com.example.seapedia.presentation.seller.product.update.image

import android.net.Uri
import com.example.seapedia.domain.entities.ProductImageEntity
import com.example.seapedia.global.utils.CommonState

data class ProductSellerUpdateImageState(
    val listImageDelete: List<Int> = listOf<Int>(),
    val listImageAdd: List<Uri> = listOf<Uri>(),
    val listImage: CommonState<List<ProductImageEntity>> = CommonState.Loading(),
    val loading: Boolean = true
)