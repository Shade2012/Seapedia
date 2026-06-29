package com.example.seapedia.domain.repositories

import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

interface SystemRepository {
    @OptIn(ExperimentalTime::class)
    suspend fun getDay() : Flow<CommonState<Instant>>
}