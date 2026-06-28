package com.example.seapedia.domain.usecases.address

import com.example.seapedia.data.remote.body.AddressBody
import com.example.seapedia.domain.repositories.AddressRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
){
    suspend fun run(body: AddressBody): Flow<CommonState<String>>{
        return addressRepository.createAddress(body)
    }
}