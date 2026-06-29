package com.example.seapedia.presentation.driver.job_detail

import com.example.seapedia.domain.entities.Job
import com.example.seapedia.global.utils.CommonState
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class JobDetailState @OptIn(ExperimentalTime::class) constructor(
    val job: CommonState<Job> = CommonState.Loading(),
    val isLoading: Boolean = false,
    val daySystem : Instant? = null,
    val isRefreshing: Boolean = false,
)
