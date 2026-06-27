package com.example.seapedia.data.remote.query

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductImagesQuery(
    @SerialName("product_id")
    private val productId: Int
): QueryParams