package com.example.seapedia.domain.usecases.address

import com.example.seapedia.data.remote.body.AddressBody
import com.example.seapedia.domain.repositories.AddressRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
){
    suspend fun run(id:Int, body: AddressBody): Flow<CommonState<String>>{
        return addressRepository.updateAddress(id,body)
    }
}