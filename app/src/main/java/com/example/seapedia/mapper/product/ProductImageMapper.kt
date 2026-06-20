package com.example.seapedia.mapper.product

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductImageResponse
import com.example.seapedia.domain.entities.ProductImageEntity
import com.example.seapedia.global.utils.Mapper

class ProductImageMapper : Mapper<BaseResponse<ProductImageResponse>, ProductImageEntity>{
    override fun mapFromResponse(type: BaseResponse<ProductImageResponse>): ProductImageEntity {
        val item = type.data
        return ProductImageRawMapper().mapFromResponse(item)
    }

}
class ProductImageRawMapper : Mapper<ProductImageResponse, ProductImageEntity>{
    override fun mapFromResponse(type: ProductImageResponse): ProductImageEntity {
        val item = type
        return ProductImageEntity(
            id = item.id,
            imageUrl = item.imageUrl
        )
    }

}