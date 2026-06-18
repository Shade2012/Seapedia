package com.example.base_compose.data.remote.body

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(val email:String, val password:String)