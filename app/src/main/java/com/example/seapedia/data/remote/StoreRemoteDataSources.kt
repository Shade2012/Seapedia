package com.example.seapedia.data.remote

import android.content.Context
import android.net.Uri
import com.example.seapedia.data.remote.body.StoreBody
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.BaseResponseNullable
import com.example.seapedia.data.remote.responses.StoreResponse
import com.example.seapedia.data.remote.services.StoreService
import com.example.seapedia.global.utils.toMultipart
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRemoteDataSources @Inject constructor(
    private val storeService: StoreService,
) {
    suspend fun getValidStore(): BaseResponse<Boolean> = storeService.getValidStore()
    suspend fun getStoreBySeller(): BaseResponseNullable<StoreResponse?> = storeService.getStoreBySeller()

    suspend fun createStoreBySeller(storeBody: StoreBody,image: MultipartBody.Part): BaseResponse<StoreResponse> = storeService.createStore(
        image = image,
        fields = mapOf(
            "name" to storeBody.name.toRequestBody(),
            "phone_number" to storeBody.phoneNumber.toRequestBody(),
            "address" to storeBody.address.toRequestBody(),
            "province" to storeBody.province.toRequestBody(),
            "city" to storeBody.city.toRequestBody(),
            "district" to storeBody.district.toRequestBody(),
            "village" to storeBody.village.toRequestBody(),
            "province_id" to storeBody.provinceId.toRequestBody(),
            "city_id" to storeBody.cityId.toRequestBody(),
            "district_id" to storeBody.districtId.toRequestBody(),
            "village_id" to storeBody.villageId.toRequestBody()
        )
    )

    suspend fun updateStoreBySeller(storeBody: StoreBody,image: MultipartBody.Part?): BaseResponse<StoreResponse> = storeService.updateStore(
        image = image,
        fields = mapOf(
            "name" to storeBody.name.toRequestBody(),
            "phone_number" to storeBody.phoneNumber.toRequestBody(),
            "address" to storeBody.address.toRequestBody(),
            "province" to storeBody.province.toRequestBody(),
            "city" to storeBody.city.toRequestBody(),
            "district" to storeBody.district.toRequestBody(),
            "village" to storeBody.village.toRequestBody(),
            "province_id" to storeBody.provinceId.toRequestBody(),
            "city_id" to storeBody.cityId.toRequestBody(),
            "district_id" to storeBody.districtId.toRequestBody(),
            "village_id" to storeBody.villageId.toRequestBody()
        )
    )
}