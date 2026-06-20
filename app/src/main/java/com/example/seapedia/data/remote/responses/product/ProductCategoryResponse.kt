package com.example.seapedia.data.remote.responses.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductCategoryResponse(
    val id:Int,
    val name: String
)