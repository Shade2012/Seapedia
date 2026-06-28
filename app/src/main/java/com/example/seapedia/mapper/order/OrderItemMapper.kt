package com.example.seapedia.mapper.order

import com.example.seapedia.data.remote.responses.order.OrderItemResponse
import com.example.seapedia.data.remote.responses.order.OrderResponse
import com.example.seapedia.data.remote.responses.order.ProductOrderResponse
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.OrderItem
import com.example.seapedia.domain.entities.OrderItemType
import com.example.seapedia.domain.entities.ProductOrder
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.mapper.product.ProductCategoryMapper
import com.example.seapedia.mapper.product.ProductCategoryRawMapper
import com.example.seapedia.mapper.product.ProductImageRawMapper

class OrderItemMapper: Mapper<OrderItemResponse, OrderItem> {
    override fun mapFromResponse(type: OrderItemResponse): OrderItem {
        val orderItemTypeMapper = OrderItemTypeMapper()
        return OrderItem(
            id = type.id,
            subTotal = type.subTotal,
            quantity = type.quantity,
            promoDiscount = type.promoDiscount,
            orderItemType = type.orderItemType.map {
                orderItemTypeMapper.mapFromResponse(it)
            },
            product = ProductOrderMapper().mapFromResponse(type.product)
        )
    }
}

class ProductOrderMapper: Mapper<ProductOrderResponse, ProductOrder> {
    override fun mapFromResponse(type: ProductOrderResponse): ProductOrder {
        return ProductOrder(
            id = type.id,
            name = type.name,
            price = type.price,
            stock = type.stock,
            isAvailable = type.isAvailable,
            listImages = type.listImages.map {
                ProductImageRawMapper().mapFromResponse(it)
            },
            category = type.category?.let {
                ProductCategoryRawMapper().mapFromResponse(it)
            }
        )
    }
}