package com.example.seapedia.data.remote.responses.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductImageResponse(
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String
)