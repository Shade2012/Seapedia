package com.example.base_compose.presentation.auth.login

data class LoginState(
    val email:String = "user2@gmail.com",
    val password: String = "1#aBer22",
    val loading: Boolean = false,
    val error : String = "",
    val emailError : Boolean = false,
    val passwordError : Boolean = false
)