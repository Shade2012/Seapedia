package com.example.seapedia.data.remote.body

import com.example.seapedia.global.utils.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(val email:String, val password:String,val role: String)