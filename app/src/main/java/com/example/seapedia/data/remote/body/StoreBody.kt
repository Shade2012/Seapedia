package com.example.seapedia.data.remote.body

data class StoreBody(
    val name: String,
    val address: String,
    val phoneNumber: String,
    val province: String,
    val city: String,
    val district: String,
    val village: String,
    val provinceId: String,
    val cityId: String,
    val districtId: String,
    val villageId: String,
)