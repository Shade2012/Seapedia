package com.example.seapedia.mapper

import com.example.seapedia.data.remote.responses.LoginResponse
import com.example.seapedia.global.utils.Mapper

class LoginMapper : Mapper<LoginResponse, String> {
    override fun mapFromResponse(type: LoginResponse): String {
        return type.token
    }
}