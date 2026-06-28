package com.example.seapedia.data.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.seapedia.data.remote.StoreRemoteDataSources
import com.example.seapedia.data.remote.body.StoreBody
import com.example.seapedia.domain.entities.Store
import com.example.seapedia.domain.repositories.StoreRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.global.utils.toMultipart
import com.example.seapedia.mapper.StoreRawMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class StoreRepositoryImpl @Inject constructor(
    private val storeRemoteDataSources: StoreRemoteDataSources,
    @ApplicationContext
    private val context: Context,
): StoreRepository {
    override suspend fun getValidStore(): Boolean {
        return storeRemoteDataSources.getValidStore().data
    }

    override suspend fun getStoreBySeller(): Flow<CommonState<Store?>> = flow{
        emit(CommonState.Loading())
        try {
            val response = storeRemoteDataSources.getStoreBySeller()
            if(response.data != null){
                emit(CommonState.Success<Store?>(data = StoreRawMapper().mapFromResponse(response.data)))
            }else{
                emit(CommonState.Success<Store?>(data = null))
            }
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun createStoreBySeller(storeBody: StoreBody, image: Uri): Flow<CommonState<Store>> = flow{
        emit(CommonState.Loading())
        try {
            val imageMultipart = image.toMultipart(context,"store_image")
            val response = storeRemoteDataSources.createStoreBySeller(storeBody,imageMultipart)
            emit(CommonState.Success<Store>(data = StoreRawMapper().mapFromResponse(response.data)))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            Log.d("Error Exception Repo",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun updateStoreBySeller(storeBody: StoreBody, image: Uri?): Flow<CommonState<Store>> = flow{
        emit(CommonState.Loading())
        try {
            val imageMultipart = image?.toMultipart(context,"store_image")
            val response = storeRemoteDataSources.updateStoreBySeller(storeBody,imageMultipart)
            emit(CommonState.Success<Store>(data = StoreRawMapper().mapFromResponse(response.data)))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            Log.d("Error Exception Repo",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}