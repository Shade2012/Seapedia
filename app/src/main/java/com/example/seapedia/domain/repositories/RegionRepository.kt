package com.example.seapedia.domain.repositories

import com.example.seapedia.domain.entities.Region
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface RegionRepository {
    fun getAllProvince(): Flow<CommonState<List<Region>>>
    fun getAllCity(idProvince: String): Flow<CommonState<List<Region>>>
    fun getAllDistrict(idCity: String): Flow<CommonState<List<Region>>>
    fun getAllVillage(idDistrict: String): Flow<CommonState<List<Region>>>
}
