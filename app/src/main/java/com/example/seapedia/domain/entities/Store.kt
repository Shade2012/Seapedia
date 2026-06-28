package com.example.seapedia.domain.entities

data class Store(
    val id: Int,
    val name: String,
    val address: String,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val image: String,

    val province: Region?,
    val city: Region?,
    val district: Region?,
    val village: Region?
)