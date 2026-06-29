package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.body.cart.CartItemBody
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addCartItem(body: CartItemBodySend): Flow<CommonState<String>>
    suspend fun updateCartItem(id:Int, body: CartItemBodySend): Flow<CommonState<String>>
    suspend fun getCart(): Flow<CommonState<CartResponse>>

    suspend fun deleteCartItem(id: Int): Flow<CommonState<String>>
    suspend fun deleteCart(): Flow<CommonState<String>>
}