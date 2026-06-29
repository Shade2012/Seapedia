package com.example.seapedia.data.remote.body.cart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItemBody(
    @SerialName("product_id")
    val productId: Int,
    val quantity: Int,
    val types : List<CartItemType>
)

@Serializable
data class CartItemType(
    @SerialName("product_type_id")
    val productTypeId: Int,
    val items : List<ProductTypeItem>
)

@Serializable
data class ProductTypeItem(
    @SerialName("product_type_item_id")
    val productTypeItemId : Int
)