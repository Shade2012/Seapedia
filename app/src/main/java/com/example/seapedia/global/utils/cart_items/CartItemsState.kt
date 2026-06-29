package com.example.seapedia.global.utils.cart_items

import com.example.seapedia.data.remote.responses.carts.CartItemResponse

data class CartItemsState(
    val storeId: Int? = null,
    val cartItems: List<CartItemResponse> = listOf()
)