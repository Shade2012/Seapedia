package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.OrderStatusBody
import com.example.seapedia.data.remote.body.cart.CartItemBody
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.data.remote.responses.order.OrderResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartService {
    @GET(NetworkConstant.CART)
    suspend fun getCart() : BaseResponse<CartResponse>

    @POST(NetworkConstant.CART)
    suspend fun createCart(
        @Body body: CartItemBodySend
    ): BaseMessage

    @PATCH("${NetworkConstant.CART}/{id}")
    suspend fun updateCart(
        @Path("id") id: Int,
        @Body body: CartItemBodySend
    ): BaseMessage

    @DELETE("${NetworkConstant.CART}/{id}")
    suspend fun deleteCartItem(
        @Path("id") id: Int,
    ): BaseMessage

    @DELETE("${NetworkConstant.CART}/all")
    suspend fun clearCart(
    ): BaseMessage
}