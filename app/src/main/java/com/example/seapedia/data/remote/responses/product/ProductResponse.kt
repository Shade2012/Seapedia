package com.example.seapedia.data.remote.responses.product

import com.example.seapedia.data.remote.responses.StoreResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Int,
    val name: String,
    val price : Int,
    val stock : Int,
    @SerialName("is_available")
    val isAvailable: Boolean,
    val images : List<ProductImageResponse>,
    val types : List<ProductTypeResponse>,
    val store : StoreResponse? = null,
    val category : ProductCategoryResponse? = null
)