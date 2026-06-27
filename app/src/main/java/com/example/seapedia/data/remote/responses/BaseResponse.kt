package com.example.seapedia.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val message:String,
    val data:T
)

@Serializable
data class BaseMessage(
    val message:String
)


@Serializable
data class BaseResponseNullable<T>(
    val message:String,
    val data:T? = null
)