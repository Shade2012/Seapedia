package com.example.seapedia.domain.entities

import androidx.compose.runtime.Immutable
import com.example.seapedia.data.remote.responses.order.DeliveryMethod
import com.example.seapedia.data.remote.responses.order.OrderStatus
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Immutable
data class Order @OptIn(ExperimentalTime::class) constructor(
    val id:Int,
    val deliveryMethod: DeliveryMethod,
    val distanceJourneyKm:Int,
    val overdue: Instant,
    val deliveryFee: Int,
    val taxFee: Int,
    val subTotal: Int,
    val totalFee:Int,
    val voucherDiscount:Int,
    val status: OrderStatus,
    val orderItems : List<OrderItem>,
    val driver: Driver?,
    val store: Store,
    val orderAddress: OrderAddress,
    val createdAt: Instant,
)

@Immutable
data class OrderAddress (
    val id:Int,
    val receiverName: String,
    val receiverPhoneNumber: String,
    val receiverAddress: String,
    val latitude: String,
    val longitude: String
)
@Immutable
data class OrderItem(
    val id: Int,
    val subTotal: Int,
    val quantity: Int,
    val promoDiscount: Int,
    val orderItemType: List<OrderItemType>,
    val product: ProductOrder
)
@Immutable
data class OrderItemType(
    val id: Int,
    val productType: ProductTypeOrder,
    val productTypeItem: List<ProductTypeItemOrder>
)
@Immutable
data class ProductOrder(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int,
    val isAvailable: Boolean,
    val listImages: List<ProductImage>,
    val category: ProductCategoryEntity? = null,
)
@Immutable
data class ProductTypeOrder(
    val id:Int,
    val name: String,
    val isMultiple: Boolean,
    val isRequired: Boolean
)
@Immutable
data class ProductTypeItemOrder(
    val id: Int,
    val productTypeItemOrderItem: ProductTypeItemOrderItem
)
@Immutable
data class ProductTypeItemOrderItem(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int
)
