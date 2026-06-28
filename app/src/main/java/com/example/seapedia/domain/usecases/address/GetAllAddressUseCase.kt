package com.example.seapedia.domain.usecases.address

import com.example.seapedia.domain.entities.Address
import com.example.seapedia.domain.repositories.AddressRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
) {
    suspend fun run() : Flow<CommonState<List<Address>>>{
        return addressRepository.getAddresses()
    }
}