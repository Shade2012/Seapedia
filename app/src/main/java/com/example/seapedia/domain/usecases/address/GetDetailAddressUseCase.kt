package com.example.seapedia.domain.usecases.address

import com.example.seapedia.data.remote.body.AddressBody
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.domain.repositories.AddressRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
){
    suspend fun run(id:Int): Flow<CommonState<Address>>{
        return addressRepository.getAddressById(id)
    }
}