package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.RegionResponse
import com.example.seapedia.data.remote.services.RegionService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionRemoteDataSources @Inject constructor(
    private val regionService: RegionService
){
    suspend fun getProvinces(): BaseResponse<List<RegionResponse>> = regionService.getAllProvince()
    suspend fun getCities(id: String): BaseResponse<List<RegionResponse>> = regionService.getAllCity(id)
    suspend fun getDistricts(id: String): BaseResponse<List<RegionResponse>> = regionService.getAllDistrict(id)
    suspend fun getVillages(id: String): BaseResponse<List<RegionResponse>> = regionService.getAllVillages(id)
}