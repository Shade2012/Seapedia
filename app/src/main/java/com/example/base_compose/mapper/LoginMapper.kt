package com.example.base_compose.mapper

import com.example.base_compose.data.remote.responses.LoginResponse
import com.example.base_compose.global.utils.Mapper

class LoginMapper : Mapper<LoginResponse, String> {
    override fun mapFromResponse(type: LoginResponse): String {
        return type.token
    }
}