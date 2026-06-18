package com.example.base_compose.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (val message:String,val token: String)