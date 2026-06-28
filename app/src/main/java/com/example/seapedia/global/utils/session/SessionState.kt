package com.example.seapedia.global.utils.session

import com.example.seapedia.global.utils.UserRole

data class SessionState(
    val isLoggedIn: Boolean = false,
    val role: UserRole? = null,
    val accessToken: String? = null,
    val isValidBuyer: Boolean = false
)