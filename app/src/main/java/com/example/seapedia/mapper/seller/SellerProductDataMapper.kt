package com.example.seapedia.mapper.seller

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductCategoryResponse
import com.example.seapedia.data.remote.responses.product.SellerProductsResponse
import com.example.seapedia.domain.entities.ProductCategoryEntity
import com.example.seapedia.domain.entities.SellerProductEntity
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.mapper.product.ProductCategoryRawMapper
import com.example.seapedia.mapper.product.ProductRawMapper

class SellerProductMapper : Mapper<BaseResponse<SellerProductsResponse>, SellerProductEntity>{
    override fun mapFromResponse(type: BaseResponse<SellerProductsResponse>): SellerProductEntity {
        val item = type.data
        return SellerProductEntity(
            countAvailable = item.available.count,
            countUnAvailable = item.unavailable.count,
            productsAvailable = item.available.products.map {
                ProductRawMapper().mapFromResponse(it)
            },
            productsUnavailable = item.unavailable.products.map {
                ProductRawMapper().mapFromResponse(it)
            },
            total = item.total,
        )
    }
}
