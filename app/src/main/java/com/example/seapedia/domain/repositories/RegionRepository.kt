package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.entities.RegionEntity
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface RegionRepository {
    fun getAllProvince(): Flow<CommonState<List<RegionEntity>>>
    fun getAllCity(idProvince: String): Flow<CommonState<List<RegionEntity>>>
    fun getAllDistrict(idCity: String): Flow<CommonState<List<RegionEntity>>>
    fun getAllVillage(idDistrict: String): Flow<CommonState<List<RegionEntity>>>
}
