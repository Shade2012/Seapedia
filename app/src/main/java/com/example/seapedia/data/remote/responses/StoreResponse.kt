package com.example.seapedia.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreResponse(
    val id: Int,
    val name: String,
    val address: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    @SerialName("image_url")
    val imageUrl: String?,

    val province: RegionResponse? = null,
    val city: RegionResponse? = null,
    val district: RegionResponse? = null,
    val village: RegionResponse? = null,
)