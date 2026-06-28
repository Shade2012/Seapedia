package com.example.seapedia.domain.usecases.address

import com.example.seapedia.domain.entities.Address
import com.example.seapedia.domain.repositories.AddressRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
) {
    suspend fun run(id: Int) : Flow<CommonState<String>>{
        return addressRepository.deleteAddress(id)
    }
}