package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.services.BuyerService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuyerRemoteDataSources @Inject constructor(
    private val buyerService: BuyerService,
) {
    suspend fun checkValidBuyer() : BaseResponse<Boolean> = buyerService.checkValidBuyer()

}