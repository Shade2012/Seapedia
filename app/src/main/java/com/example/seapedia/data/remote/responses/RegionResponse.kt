package com.example.seapedia.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionResponse(
    val id: Int,
    val name: String
)