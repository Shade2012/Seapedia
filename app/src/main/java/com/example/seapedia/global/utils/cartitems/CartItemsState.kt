package com.example.seapedia.global.utils.cartitems

import com.example.seapedia.data.remote.responses.carts.CartItemResponse
import com.example.seapedia.domain.entities.CartItem

data class CartItemsState(
    val storeId: Int? = null,
    val cartItems: List<CartItemResponse> = listOf()
)