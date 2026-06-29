package com.example.seapedia.presentation.add_role

import com.example.seapedia.global.utils.UserRole

data class AddRoleState(
    val role: UserRole? = null,
    val isLoading: Boolean = false
)