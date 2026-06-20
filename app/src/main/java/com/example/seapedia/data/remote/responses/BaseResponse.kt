package com.example.seapedia.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(val message:String,val data:T)