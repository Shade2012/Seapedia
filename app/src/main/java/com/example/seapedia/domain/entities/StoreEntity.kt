package com.example.seapedia.domain.entities

data class StoreEntity(
    val id: Int,
    val name: String,
    val address: String,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val image: String
)