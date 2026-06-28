package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.body.AddressBody
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun getAddresses(): Flow<CommonState<List<Address>>>
    suspend fun getAddressById(id: Int): Flow<CommonState<Address>>
    suspend fun createAddress(body: AddressBody): Flow<CommonState<String>>
    suspend fun updateAddress(id: Int,body: AddressBody): Flow<CommonState<String>>
    suspend fun deleteAddress(id: Int): Flow<CommonState<String>>

}