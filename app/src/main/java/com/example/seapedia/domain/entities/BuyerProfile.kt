package com.example.seapedia.domain.entities

data class BuyerProfile(
    val user: UserProfile,
    val phoneNumber: String?
)