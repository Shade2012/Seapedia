package com.example.seapedia.presentation.seller.product.update.image

import android.net.Uri
import com.example.seapedia.domain.entities.ProductImage
import com.example.seapedia.global.utils.CommonState

data class ProductSellerUpdateImageState(
    val listImageDelete: List<Int> = listOf<Int>(),
    val listImageAdd: List<Uri> = listOf<Uri>(),
    val listImage: CommonState<List<ProductImage>> = CommonState.Loading(),
    val isLoading: Boolean = true
)