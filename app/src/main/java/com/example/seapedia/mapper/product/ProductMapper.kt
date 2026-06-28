package com.example.seapedia.mapper.product

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.mapper.StoreRawMapper


class ProductMapper : Mapper<BaseResponse<ProductResponse>, Product>{
    override fun mapFromResponse(type: BaseResponse<ProductResponse>): Product {
        val product = type.data
        return ProductRawMapper().mapFromResponse(product)
    }
}
class ProductRawMapper : Mapper<ProductResponse, Product>{
    override fun mapFromResponse(type: ProductResponse): Product {
        val product = type
        return Product(
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