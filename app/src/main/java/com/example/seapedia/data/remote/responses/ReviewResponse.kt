package com.example.seapedia.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse (
    val id: Int,
    @SerialName("reviewer_name")
    val reviewerName: String,
    val comment: String,
    val rating: Int,
    val createdAt: String
)