package com.example.seapedia.data.remote.responses.carts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("updatedAt")
    val updatedAt: String,

    @SerialName("deleted_at")
    val deletedAt: String?,

    @SerialName("id")
    val id: Int,

    @SerialName("store_id")
    val storeId: Int?,

    @SerialName("sub_total")
    val subTotal: Int,

    @SerialName("cartItems")
    val cartItems: List<CartItemResponse>
)

@Serializable
data class CartItemResponse(
    @SerialName("createdAt")
    val createdAt: String? ="",

    @SerialName("updatedAt")
    val updatedAt: String? ="",

    @SerialName("deleted_at")
    val deletedAt: String? = "",

    @SerialName("id")
    val id: Int,

    @SerialName("quantity")
    val quantity: Int,

    @SerialName("product")
    val product: ProductResponse,

    @SerialName("sub_total")
    val subTotal: Int,

    @SerialName("cartProductTypes")
    val cartProductTypes: List<CartProductTypeResponse>
)

@Serializable
data class ProductResponse(
    @SerialName("created_at")
    val createdAt: String? = "",

    @SerialName("updated_at")
    val updatedAt: String? = "",

    @SerialName("deleted_at")
    val deletedAt: String? = "",

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("price")
    val price: Int,

    @SerialName("stock")
    val stock: Int,

    @SerialName("is_available")
    val isAvailable: Boolean,

    @SerialName("promo")
    val promo: PromoResponse?,

    @SerialName("images")
    val images: List<ProductImageResponse>
)

@Serializable
data class PromoResponse(
    @SerialName("created_at")
    val createdAt: String? = "",

    @SerialName("updated_at")
    val updatedAt: String? = "",

    @SerialName("deleted_at")
    val deletedAt: String? = "",

    @SerialName("id")
    val id: Int,

    @SerialName("discount")
    val discount: DiscountResponse
)

@Serializable
data class DiscountResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("discount_percantage")
    val discountPercentage: Int,

    @SerialName("type")
    val type: String,

    @SerialName("remaining_usage")
    val remainingUsage: Int,

    @SerialName("expired_date")
    val expiredDate: String,

    @SerialName("max_usage_per_user")
    val maxUsagePerUser: Int
)

@Serializable
data class ProductImageResponse(
    @SerialName("created_at")
    val createdAt: String? ="",

    @SerialName("updated_at")
    val updatedAt: String? ="",

    @SerialName("deleted_at")
    val deletedAt: String? = "",

    @SerialName("id")
    val id: Int,

    @SerialName("image_url")
    val imageUrl: String
)

@Serializable
data class CartProductTypeResponse(
    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("deleted_at")
    val deletedAt: String?,

    @SerialName("id")
    val id: Int,

    @SerialName("product_type")
    val productType: ProductTypeResponse,

    @SerialName("cart_product_type_items")
    val cartProductTypeItems: List<CartProductTypeItemResponse>
)

@Serializable
data class ProductTypeResponse(
    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("deleted_at")
    val deletedAt: String?,

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("is_multiple")
    val isMultiple: Boolean,

    @SerialName("is_required")
    val isRequired: Boolean
)

@Serializable
data class CartProductTypeItemResponse(
    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("deleted_at")
    val deletedAt: String?,

    @SerialName("id")
    val id: Int,

    @SerialName("product_type_item")
    val productTypeItem: ProductTypeItemResponse
)

@Serializable
data class ProductTypeItemResponse(
    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("deleted_at")
    val deletedAt: String?,

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("stock")
    val stock: Int,

    @SerialName("price")
    val price: Int
)