package com.example.seapedia.data.remote.body

import com.example.seapedia.global.utils.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterBody(
    @SerialName("full_name")
    val fullName: String,
    val email: String, val password: String,val role: String)
