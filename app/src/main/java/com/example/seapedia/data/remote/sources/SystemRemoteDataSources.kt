package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.services.SystemService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemRemoteDataSources @Inject constructor(
    private val systemService: SystemService
){
    suspend fun getDay() : BaseResponse<String>{
        return systemService.getDay()
    }
}