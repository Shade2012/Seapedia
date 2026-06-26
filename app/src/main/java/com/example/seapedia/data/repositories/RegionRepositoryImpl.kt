package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.RegionRemoteDataSources
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.entities.RegionEntity
import com.example.seapedia.domain.repositories.RegionRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.RegionMapper
import com.example.seapedia.mapper.product.ProductRawMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class RegionRepositoryImpl @Inject constructor(
    private val regionRemoteDataSources: RegionRemoteDataSources
): RegionRepository {
    override fun getAllProvince(): Flow<CommonState<List<RegionEntity>>> = flow{
        emit(CommonState.Loading())
        try {
            val mapper = RegionMapper()
            val response = regionRemoteDataSources.getProvinces()
            emit(CommonState.Success<List<RegionEntity>>(
                data = response.data.map(mapper::mapFromResponse)
            ))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override fun getAllCity(idProvince: String): Flow<CommonState<List<RegionEntity>>> = flow{
        emit(CommonState.Loading())
        try {
            val mapper = RegionMapper()
            val response = regionRemoteDataSources.getCities(idProvince)
            emit(CommonState.Success<List<RegionEntity>>(
                data = response.data.map(mapper::mapFromResponse)
            ))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override fun getAllDistrict(idCity: String): Flow<CommonState<List<RegionEntity>>> = flow{
        emit(CommonState.Loading())
        try {
            val mapper = RegionMapper()
            val response = regionRemoteDataSources.getDistricts(idCity)
            emit(CommonState.Success<List<RegionEntity>>(
                data = response.data.map(mapper::mapFromResponse)
            ))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override fun getAllVillage(idDistrict: String): Flow<CommonState<List<RegionEntity>>> = flow{
        emit(CommonState.Loading())
        try {
            val mapper = RegionMapper()
            val response = regionRemoteDataSources.getVillages(idDistrict)
            emit(CommonState.Success<List<RegionEntity>>(
                data = response.data.map(mapper::mapFromResponse)
            ))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

}