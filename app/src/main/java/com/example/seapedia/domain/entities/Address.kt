package com.example.seapedia.domain.entities

import com.example.seapedia.data.remote.responses.RegionResponse
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Address @OptIn(ExperimentalTime::class) constructor(
    val id:Int,
    val name: String,
    val receiverName:String,
    val addressDetail: String,
    val longitude: String,
    val latitude: String,
    val province: Region? = null,
    val city: Region? = null,
    val district: Region? = null,
    val village: Region? = null,
)