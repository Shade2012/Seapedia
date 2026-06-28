package com.example.seapedia.domain.entities

import com.example.seapedia.data.remote.body.ReviewBody
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Review @OptIn(ExperimentalTime::class) constructor(
    val id: Int = 0,
    val reviewerName: String,
    val comment: String,
    val rating: Int,
    val createdAt : Instant = Instant.DISTANT_PAST
)
fun Review.toBody() : ReviewBody{
    return ReviewBody(
        reviewerName = reviewerName,
        comment = comment,
        rating = rating
    )
}