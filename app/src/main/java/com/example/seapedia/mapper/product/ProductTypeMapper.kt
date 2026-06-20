package com.example.seapedia.mapper.product

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductTypeResponse
import com.example.seapedia.domain.entities.ProductType
import com.example.seapedia.global.utils.Mapper



class ProductTypeMapper : Mapper<BaseResponse<ProductTypeResponse>, ProductType>{
    override fun mapFromResponse(type: BaseResponse<ProductTypeResponse>): ProductType {
        val type = type.data
        return ProductTypeRawMapper().mapFromResponse(type)
    }

}
class ProductTypeRawMapper : Mapper<ProductTypeResponse, ProductType>{
    override fun mapFromResponse(type: ProductTypeResponse): ProductType {
        val type = type
        return ProductType(
            id = type.id,
            name = type.name,
            isMultiple = type.isMultiple,
            isRequired = type.isRequired,
            listItems = type.items.map {
                ProductTypeItemRawMapper().mapFromResponse(it)
            },
        )
    }

}