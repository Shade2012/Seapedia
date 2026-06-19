package com.example.seapedia.presentation.auth.register

import com.example.seapedia.global.utils.UserRole

data class RegisterState(
    val email:String = "shade@gmail.com",
    val fullName:String = "Shade Kabrun",
    val password: String = "12345678",
    val selectedRole: UserRole? = null,
    val loading: Boolean = false,
    val error : String = "",
    val emailError : Boolean = false,
    val passwordError : Boolean = false,
    val isPasswordVisible : Boolean = false
)