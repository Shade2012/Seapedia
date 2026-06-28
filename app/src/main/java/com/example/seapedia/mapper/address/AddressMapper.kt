package com.example.seapedia.mapper.address

import com.example.seapedia.data.remote.responses.AddressResponse
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.mapper.RegionMapper

class AddressListMapper : Mapper<BaseResponse<List<AddressResponse>>, List<Address>>{
    val mapper = AddressRawMapper()
    override fun mapFromResponse(type: BaseResponse<List<AddressResponse>>): List<Address> {
        return type.data.map {
            mapper.mapFromResponse(it)
        }
    }
}

class AddressMapper : Mapper<BaseResponse<AddressResponse>, Address>{
    val mapper = AddressRawMapper()
    override fun mapFromResponse(type: BaseResponse<AddressResponse>): Address {
        return AddressRawMapper().mapFromResponse(type.data)
    }
}
class AddressRawMapper : Mapper<AddressResponse, Address> {
    override fun mapFromResponse(type: AddressResponse): Address {
        val regionMapper = RegionMapper()
        return Address(
            id = type.id,
            addressDetail = type.addressDetail,
            name = type.name,
            receiverName = type.receiverName,
            longitude = type.longitude,
            latitude = type.latitude,
            province = type.province?.let {
                regionMapper.mapFromResponse(type.province)
            },
            city = type.city?.let {
                regionMapper.mapFromResponse(type.city)
            },
            district = type.district?.let {
                regionMapper.mapFromResponse(type.district)
            },
            village = type.village?.let {
                regionMapper.mapFromResponse(type.village)
            },
        )
    }
}
