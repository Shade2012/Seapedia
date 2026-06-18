package com.example.base_compose.global.utils

interface Mapper<Response,Model> {
    fun mapFromResponse(type:Response):Model
}