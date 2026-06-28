package com.example.seapedia.mapper

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.StoreResponse
import com.example.seapedia.domain.entities.Store
import com.example.seapedia.global.utils.Mapper


class StoreMapper : Mapper<BaseResponse<StoreResponse>, Store>{
    override fun mapFromResponse(type: BaseResponse<StoreResponse>): Store {
        val store = type.data
        return StoreRawMapper().mapFromResponse(store)
    }

}
class StoreRawMapper : Mapper<StoreResponse, Store>{
    override fun mapFromResponse(type: StoreResponse): Store {
        val store = type
        return Store(
            id = store.id,
            name = store.name,
            address = store.address,
            image = store.imageUrl ?: "",
            latitude = store.latitude,
            longitude = store.longitude,
            phoneNumber = store.phoneNumber,
            province = type.province?.let {
                RegionMapper().mapFromResponse(it)
            },
            city = type.city?.let {
                RegionMapper().mapFromResponse(it)
            },
            district = type.district?.let {
                RegionMapper().mapFromResponse(it)
            },
            village = type.village?.let {
                RegionMapper().mapFromResponse(it)
            }
        )
    }

}