package com.example.seapedia.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class DriverResponse (
    val id:Int,
    val user: UserResponse
)