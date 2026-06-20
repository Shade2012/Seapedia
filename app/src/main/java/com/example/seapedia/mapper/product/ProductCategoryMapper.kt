package com.example.seapedia.mapper.product

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductCategoryResponse
import com.example.seapedia.domain.entities.ProductCategoryEntity
import com.example.seapedia.global.utils.Mapper


class ProductCategoryMapper : Mapper<BaseResponse<ProductCategoryResponse>, ProductCategoryEntity>{
    override fun mapFromResponse(type: BaseResponse<ProductCategoryResponse>): ProductCategoryEntity {
        val item = type.data
        return ProductCategoryRawMapper().mapFromResponse(item)
    }

}
class ProductCategoryRawMapper : Mapper<ProductCategoryResponse, ProductCategoryEntity>{
    override fun mapFromResponse(type: ProductCategoryResponse): ProductCategoryEntity {
        val item = type
        return ProductCategoryEntity(
            id = item.id,
            name = item.name
        )
    }

}