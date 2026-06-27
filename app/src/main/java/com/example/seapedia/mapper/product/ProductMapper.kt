package com.example.seapedia.mapper.product

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.mapper.StoreRawMapper


class ProductMapper : Mapper<BaseResponse<ProductResponse>, ProductEntity>{
    override fun mapFromResponse(type: BaseResponse<ProductResponse>): ProductEntity {
        val product = type.data
        return ProductRawMapper().mapFromResponse(product)
    }
}
class ProductRawMapper : Mapper<ProductResponse, ProductEntity>{
    override fun mapFromResponse(type: ProductResponse): ProductEntity {
        val product = type
        return ProductEntity(
            name = product.name,
            stock = product.stock,
            id = product.id,
            price = product.price,
            category = product.category?.let(ProductCategoryRawMapper()::mapFromResponse),
            isAvailable = product.isAvailable,
            store = product.store?.let(StoreRawMapper()::mapFromResponse),
            types = product.types.map {
                    typeResponse -> ProductTypeRawMapper().mapFromResponse(typeResponse)
            },
            listImages = product.images.map {
                    image ->
                ProductImageRawMapper().mapFromResponse(type = image)
            }.toList()
        )
    }
}