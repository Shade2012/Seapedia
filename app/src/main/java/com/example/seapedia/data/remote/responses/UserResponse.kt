package com.example.seapedia.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("full_name")
    val fullName:String,
    val id: Int,
    val email: String
)