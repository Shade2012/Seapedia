package com.example.seapedia.data.remote.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressBody(
    val name: String,
    @SerialName("receiver_name")
    val receiverName: String,
    @SerialName("address_detail")
    val addressDetail: String,
    @SerialName("province_id")
    val provinceId: Int,
    val province: String,
    @SerialName("city_id")
    val cityId: Int,
    val city: String,
    @SerialName("district_id")
    val districtId: Int,
    val district: String,
    @SerialName("village_id")
    val villageId: Int,
    val village: String,
)