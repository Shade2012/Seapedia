package com.example.seapedia.data.remote.responses.order

import com.example.seapedia.data.remote.responses.JobResponse
import com.example.seapedia.data.remote.responses.StoreResponse
import com.example.seapedia.data.remote.responses.product.ProductCategoryResponse
import com.example.seapedia.data.remote.responses.product.ProductImageResponse
import com.example.seapedia.domain.entities.Driver
import com.example.seapedia.domain.entities.OrderAddress
import com.example.seapedia.domain.entities.Store
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

@Serializable
data class OrderResponse @OptIn(ExperimentalTime::class) constructor(
    val id:Int,
    @SerialName("delivery_method")
    val deliveryMethod: DeliveryMethod,
    @SerialName("distance_journey")
    val distanceJourneyKm:Int,
    val overdue: String,
    @SerialName("delivery_fee")
    val deliveryFee: Int,
    @SerialName("tax_fee")
    val taxFee: Int,
    @SerialName("sub_total")
    val subTotal: Int,
    @SerialName("total_fee")
    val totalFee:Int,
    @SerialName("voucher_discount")
    val voucherDiscount:Int,
    @SerialName("status")
    val status: OrderStatus,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("order_items")
    val orderItemResponses : List<OrderItemResponse>,
    @SerialName("job")
    val jobResponse: JobResponse,
    @SerialName("order_address")
    val orderAddressResponse: OrderAddressResponse,
    val store: StoreResponse
)


@Serializable
data class OrderAddressResponse(
    val id: Int,
    @SerialName("receiver_name")
    val receiverName: String,
    @SerialName("receiver_phone_number")
    val receiverPhoneNumber: String,
    @SerialName("receiver_address")
    val receiverAddress: String,
    val latitude: String,
    val longitude: String
)

@Serializable
data class OrderItemResponse(
    val id: Int,
    @SerialName("sub_total")
    val subTotal: Int,
    val quantity: Int,
    @SerialName("promo_discount")
    val promoDiscount: Int,
    @SerialName("types")
    val orderItemType: List<OrderItemTypeResponse>,
    val product: ProductOrderResponse
)

@Serializable
data class OrderItemTypeResponse(
    val id: Int,
    @SerialName("type")
    val productType: ProductTypeOrderResponse,
    @SerialName("items")
    val productTypeItem: List<ProductTypeItemOrderResponse>
)

@Serializable
data class ProductOrderResponse(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int,
    @SerialName("is_available")
    val isAvailable: Boolean,
    @SerialName("images")
    val listImages: List<ProductImageResponse>,
    val category: ProductCategoryResponse? = null,
)
@Serializable
data class ProductTypeOrderResponse(
    val id:Int,
    val name: String,
    @SerialName("is_multiple")
    val isMultiple: Boolean,
    @SerialName("is_required")
    val isRequired: Boolean
)
@Serializable
data class ProductTypeItemOrderResponse(
    val id: Int,
    @SerialName("item")
    val productTypeItemOrderItemResponse: ProductTypeItemOrderItemResponse
)

@Serializable
data class ProductTypeItemOrderItemResponse(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int
)