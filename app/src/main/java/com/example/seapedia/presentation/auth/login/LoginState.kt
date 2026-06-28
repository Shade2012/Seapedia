package com.example.seapedia.presentation.auth.login

import com.example.seapedia.global.utils.UserRole

data class LoginState(
    val email:String = "user1@gmail.com",
    val password: String = "12345678",
    val selectedRole: UserRole? = null,
    val isLoading: Boolean = false,
    val error : String = "",
    val emailError : Boolean = false,
    val passwordError : Boolean = false,
    val isPasswordVisible : Boolean = false
)