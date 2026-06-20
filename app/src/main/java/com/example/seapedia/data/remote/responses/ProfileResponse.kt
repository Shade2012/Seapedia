package com.example.seapedia.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val id: Int,
    @SerialName("full_name")
    val fullName: String,
    val email: String,
    val roles : List<RoleResponse>
)