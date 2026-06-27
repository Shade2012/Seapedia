package com.example.seapedia.data.remote.query

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllProductSellerQuery(
    val name: String? = null,
): QueryParams