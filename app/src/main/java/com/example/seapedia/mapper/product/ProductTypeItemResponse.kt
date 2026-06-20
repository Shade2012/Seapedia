package com.example.seapedia.mapper.product

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductTypeItemResponse
import com.example.seapedia.domain.entities.ProductTypeItem
import com.example.seapedia.global.utils.Mapper


class ProductTypeItemMapper : Mapper<BaseResponse<ProductTypeItemResponse>, ProductTypeItem>{
    override fun mapFromResponse(type: BaseResponse<ProductTypeItemResponse>): ProductTypeItem {
        val item = type.data
        return ProductTypeItemRawMapper().mapFromResponse(item)
    }

}
class ProductTypeItemRawMapper : Mapper<ProductTypeItemResponse, ProductTypeItem>{
    override fun mapFromResponse(type: ProductTypeItemResponse): ProductTypeItem {
        val item = type
        return ProductTypeItem(
            id = item.id,
            name = item.name,
            price = item.price,
            stock = item.stock
        )
    }

}