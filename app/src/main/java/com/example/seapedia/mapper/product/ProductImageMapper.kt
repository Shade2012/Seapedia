package com.example.seapedia.mapper.product

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductImageResponse
import com.example.seapedia.domain.entities.ProductImage
import com.example.seapedia.global.utils.Mapper

class ProductImageMapper : Mapper<BaseResponse<ProductImageResponse>, ProductImage>{
    override fun mapFromResponse(type: BaseResponse<ProductImageResponse>): ProductImage {
        val item = type.data
        return ProductImageRawMapper().mapFromResponse(item)
    }

}
class ProductImageRawMapper : Mapper<ProductImageResponse, ProductImage>{
    override fun mapFromResponse(type: ProductImageResponse): ProductImage {
        val item = type
        return ProductImage(
            id = item.id,
            imageUrl = item.imageUrl
        )
    }

}