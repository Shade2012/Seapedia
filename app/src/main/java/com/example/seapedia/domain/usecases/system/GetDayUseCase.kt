package com.example.seapedia.domain.usecases.system

import com.example.seapedia.domain.repositories.SystemRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class GetDayUseCase @Inject constructor(
    private val systemRepository: SystemRepository
) {

    @OptIn(ExperimentalTime::class)
    suspend fun run() : Flow<CommonState<Instant>>{
        return systemRepository.getDay()
    }
}