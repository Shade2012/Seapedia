package com.example.seapedia.data.remote.body

import com.example.seapedia.data.remote.query.QueryParams
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductImageBody(
    @SerialName("product_image_ids")
    private val productImageIds: List<Int>
)