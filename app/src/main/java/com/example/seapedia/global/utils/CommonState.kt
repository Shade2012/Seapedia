package com.example.seapedia.global.utils

sealed class CommonState<T> {
    class Loading<T> : CommonState<T>()
    data class Success<T>(val data: T) : CommonState<T>()
    data class Error<T>(val message: String, val data:T? = null) : CommonState<T>()
}