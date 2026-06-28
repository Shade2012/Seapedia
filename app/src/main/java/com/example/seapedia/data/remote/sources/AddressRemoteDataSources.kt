package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.body.AddressBody
import com.example.seapedia.data.remote.responses.AddressResponse
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.services.AddressService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRemoteDataSources @Inject constructor(
    private val addressService: AddressService
){
    suspend fun getAllAddressService() : BaseResponse<List<AddressResponse>>{
        return this.addressService.getAllAddress()
    }

    suspend fun getDetailAddressService(id:Int) : BaseResponse<AddressResponse>{
        return this.addressService.getDetailAddress(id)
    }

    suspend fun createAddress(body: AddressBody): BaseMessage {
        return  this.addressService.createAddress(body)
    }

    suspend fun updateAddress(id:Int,body: AddressBody): BaseMessage {
        return  this.addressService.updateAddress(id,body)
    }
    suspend fun deleteAddress(id: Int) : BaseMessage{
        return this.addressService.deleteAddress(id)
    }
}