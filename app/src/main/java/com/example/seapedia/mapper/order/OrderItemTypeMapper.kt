package com.example.seapedia.mapper.order

import com.example.seapedia.data.remote.responses.order.OrderItemTypeResponse
import com.example.seapedia.data.remote.responses.order.ProductTypeItemOrderItemResponse
import com.example.seapedia.data.remote.responses.order.ProductTypeItemOrderResponse
import com.example.seapedia.data.remote.responses.order.ProductTypeOrderResponse
import com.example.seapedia.domain.entities.OrderItemType
import com.example.seapedia.domain.entities.ProductTypeItemOrder
import com.example.seapedia.domain.entities.ProductTypeItemOrderItem
import com.example.seapedia.domain.entities.ProductTypeOrder
import com.example.seapedia.global.utils.Mapper

class OrderItemTypeMapper: Mapper<OrderItemTypeResponse, OrderItemType> {
    override fun mapFromResponse(type: OrderItemTypeResponse): OrderItemType {
        return OrderItemType(
            id = type.id,
            productType = ProductTypeOrderMapper().mapFromResponse(type.productType),
            productTypeItem = type.productTypeItem.map {
                ProductTypeItemMapper().mapFromResponse(it)
            }
        )
    }
}
class ProductTypeItemMapper: Mapper<ProductTypeItemOrderResponse, ProductTypeItemOrder>{
    override fun mapFromResponse(type: ProductTypeItemOrderResponse): ProductTypeItemOrder {
        return ProductTypeItemOrder(
            id = type.id,
            productTypeItemOrderItem = ProductTypeOrderItemMapper().mapFromResponse(type.productTypeItemOrderItemResponse)
        )
    }

}
class ProductTypeOrderMapper: Mapper<ProductTypeOrderResponse, ProductTypeOrder>{
    override fun mapFromResponse(type: ProductTypeOrderResponse): ProductTypeOrder {
        return ProductTypeOrder(
            id = type.id,
            name = type.name,
            isMultiple = type.isMultiple,
            isRequired = type.isRequired,
        )
    }

}

class ProductTypeOrderItemMapper: Mapper<ProductTypeItemOrderItemResponse, ProductTypeItemOrderItem>{
    override fun mapFromResponse(type: ProductTypeItemOrderItemResponse): ProductTypeItemOrderItem {
        return ProductTypeItemOrderItem(
            id = type.id,
            name = type.name,
            price = type.price,
            stock = type.stock
        )
    }
}