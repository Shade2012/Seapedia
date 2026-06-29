package com.example.seapedia.data.remote.body.job

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobBody(
    @SerialName("job_id")
    val jobId: Int
)