package com.example.seapedia.data.remote.body

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(val email:String, val password:String)