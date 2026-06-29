package com.example.seapedia.data.remote.body.cart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItemBodySend(
    @SerialName("product_id")
    val productId:Int? = null,
    val quantity: Int?,
    val types : List<CartItemTypeCartItemBodySend>? = null
)

@Serializable
data class CartItemTypeCartItemBodySend(
    @SerialName("product_type_id")
    val productTypeId: Int?,
    val items : List<ProductTypeItemCartItemBodySend>?
)

@Serializable
data class ProductTypeItemCartItemBodySend(
    @SerialName("product_type_item_id")
    val productTypeItemId : Int?
)