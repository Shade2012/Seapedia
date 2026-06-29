package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.body.cart.CartItemBody
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.data.remote.services.CartService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CartRemoteDataSources @Inject constructor(
    private val cartService: CartService,
) {
    suspend fun getCart() : BaseResponse<CartResponse> = cartService.getCart()

    suspend fun updateCart(id:Int, bodySend: CartItemBodySend) : BaseMessage = cartService.updateCart(id,bodySend)

    suspend fun createCart(bodySend: CartItemBodySend) : BaseMessage = cartService.createCart(bodySend)

    suspend fun deleteCartItem(id:Int) : BaseMessage = cartService.deleteCartItem(id)

    suspend fun clearCart() : BaseMessage = cartService.clearCart()
}