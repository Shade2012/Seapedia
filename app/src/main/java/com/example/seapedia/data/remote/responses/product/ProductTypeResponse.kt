package com.example.seapedia.data.remote.responses.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductTypeResponse(
    val id: Int,
    val name: String,
    @SerialName("is_multiple")
    val isMultiple: Boolean,
    @SerialName("is_required")
    val isRequired: Boolean,
    val items: List<ProductTypeItemResponse>
)