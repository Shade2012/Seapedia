package com.example.seapedia.data.remote.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewBody(
    @SerialName("reviewer_name")
    val reviewerName: String,
    val comment: String,
    val rating: Int)


