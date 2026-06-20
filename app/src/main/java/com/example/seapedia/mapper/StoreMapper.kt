package com.example.seapedia.mapper

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.StoreResponse
import com.example.seapedia.domain.entities.StoreEntity
import com.example.seapedia.global.utils.Mapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


class StoreMapper : Mapper<BaseResponse<StoreResponse>, StoreEntity>{
    override fun mapFromResponse(type: BaseResponse<StoreResponse>): StoreEntity {
        val store = type.data
        return StoreRawMapper().mapFromResponse(store)
    }

}
class StoreRawMapper : Mapper<StoreResponse, StoreEntity>{
    override fun mapFromResponse(type: StoreResponse): StoreEntity {
        val store = type
        return StoreEntity(
            id = store.id,
            name = store.name,
            address = store.address,
            image = store.imageUrl,
            latitude = store.latitude.toDouble(),
            longitude = store.longitude.toDouble(),
            phoneNumber = store.phoneNumber
        )
    }

}