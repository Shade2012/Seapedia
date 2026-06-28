package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.body.AddressBody
import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.sources.AddressRemoteDataSources
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.domain.repositories.AddressRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.address.AddressListMapper
import com.example.seapedia.mapper.address.AddressMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AddressRepositoryImpl @Inject constructor(
    private val addressRemoteDataSources: AddressRemoteDataSources
): AddressRepository {
    override suspend fun getAddresses(): Flow<CommonState<List<Address>>> = flow{
        emit(CommonState.Loading())
        try {
            val response = addressRemoteDataSources.getAllAddressService()
            val mapped = AddressListMapper().mapFromResponse(response)
            emit(CommonState.Success<List<Address>>(data = mapped))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getAddressById(id: Int): Flow<CommonState<Address>> = flow{
        try {
            val response = addressRemoteDataSources.getDetailAddressService(id)
            val data = AddressMapper().mapFromResponse(response)
            emit(CommonState.Success<Address>(data))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun createAddress(body: AddressBody): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = addressRemoteDataSources.createAddress(body)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun updateAddress(id:Int, body: AddressBody): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = addressRemoteDataSources.updateAddress(id,body)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteAddress(id:Int): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = addressRemoteDataSources.deleteAddress(id)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}