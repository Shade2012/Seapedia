package com.example.seapedia.global.utils.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64

@Serializable
data class JwtPayload(
    val sub: Int,
    val email: String,
    val userRoleId: Int,
    val role: String,
    val iat: Long,
    val exp: Long
)

fun decodeJwt(token: String): JwtPayload {
    val payload = token.split(".")[1]

    val padded = when (payload.length % 4) {
        2 -> "$payload=="
        3 -> "$payload="
        else -> payload
    }

    val decoded = Base64.UrlSafe.decode(padded).decodeToString()

    return Json.decodeFromString(decoded)
}