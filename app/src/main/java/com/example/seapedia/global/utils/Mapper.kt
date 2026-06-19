package com.example.seapedia.global.utils

interface Mapper<Response,Model> {
    fun mapFromResponse(type:Response):Model
}