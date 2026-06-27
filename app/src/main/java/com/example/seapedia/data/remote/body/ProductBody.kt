package com.example.seapedia.data.remote.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateProductBody(
    val name: String,
    val price: Int,
    val stock: Int,
    val types: List<CreateProductType>
)

@Serializable
data class CreateProductType(
    val name: String,
    @SerialName("is_multiple")
    val isMultiple: Boolean,
    @SerialName("is_required")
    val isRequired: Boolean,
    val items: List<CreateProductTypeItem>
)

@Serializable
data class CreateProductTypeItem(
    val name: String,
    val stock: Int,
    val price: Int
)