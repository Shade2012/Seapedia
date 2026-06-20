package com.example.seapedia.data.remote.responses.product

import kotlinx.serialization.Serializable


@Serializable
data class ProductTypeItemResponse(
    val id:Int,
    val name: String,
    val price: Int,
    val stock: Int
)