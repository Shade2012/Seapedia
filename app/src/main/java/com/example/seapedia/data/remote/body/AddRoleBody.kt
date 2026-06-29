package com.example.seapedia.data.remote.body

import com.example.seapedia.global.utils.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class AddRoleBody(
    val role: UserRole
)