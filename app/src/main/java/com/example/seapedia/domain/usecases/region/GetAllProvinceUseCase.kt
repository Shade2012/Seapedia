package com.example.seapedia.domain.usecases.region

import com.example.seapedia.domain.entities.RegionEntity
import com.example.seapedia.domain.repositories.RegionRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProvinceUseCase @Inject constructor(
    private val regionRepository: RegionRepository
) {
    suspend fun run() : Flow<CommonState<List<RegionEntity>>>{
        return regionRepository.getAllProvince()
    }
}