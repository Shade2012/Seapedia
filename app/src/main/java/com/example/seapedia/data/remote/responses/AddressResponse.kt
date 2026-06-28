package com.example.seapedia.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse (
    val id: Int,
    val name: String,
    @SerialName("receiver_name")
    val receiverName: String,
    @SerialName("address_detail")
    val addressDetail: String,
    val latitude: String,
    val longitude: String,
    val province: RegionResponse? = null,
    val city: RegionResponse? = null,
    val district: RegionResponse? = null,
    val village: RegionResponse? = null,
)