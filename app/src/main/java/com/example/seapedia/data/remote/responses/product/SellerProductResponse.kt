package com.example.seapedia.data.remote.responses.product

import com.example.seapedia.data.remote.responses.StoreResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SellerProductsResponse(
    val available: SellerProductDataResponse,
    val unavailable: SellerProductDataResponse,
    val total: Int = 0
)

@Serializable
data class SellerProductDataResponse(
    val count: Int = 0,
    val products: List<ProductResponse>
)