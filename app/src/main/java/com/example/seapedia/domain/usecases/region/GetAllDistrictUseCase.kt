package com.example.seapedia.domain.usecases.region

import com.example.seapedia.domain.entities.RegionEntity
import com.example.seapedia.domain.repositories.RegionRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDistrictUseCase @Inject constructor(
    private val regionRepository: RegionRepository
) {
    suspend fun run(idCity: String) : Flow<CommonState<List<RegionEntity>>>{
        return regionRepository.getAllDistrict(idCity)
    }
}