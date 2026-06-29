package com.example.seapedia.presentation.buyer.cart

import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.domain.entities.Cart
import com.example.seapedia.global.utils.CommonState

data class CartScreenState(
    val cart: CommonState<CartResponse> = CommonState.Loading(),
    val isRefreshing: Boolean = false
)