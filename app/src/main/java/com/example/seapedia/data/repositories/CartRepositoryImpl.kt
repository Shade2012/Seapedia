package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.body.cart.CartItemBody
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.data.remote.sources.CartRemoteDataSources
import com.example.seapedia.domain.entities.Cart
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.repositories.CartRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.order.OrderMapperList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CartRepositoryImpl @Inject constructor(
    private val cartRemoteDataSources: CartRemoteDataSources
) : CartRepository{

    override suspend fun addCartItem(body: CartItemBodySend): Flow<CommonState<String>> = flow{
        try {
            val response = cartRemoteDataSources.createCart(body)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun updateCartItem(id:Int, body: CartItemBodySend): Flow<CommonState<String>> = flow{
        try {
            val response = cartRemoteDataSources.updateCart(id,body)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getCart(): Flow<CommonState<CartResponse>> =flow{
        emit(CommonState.Loading())
        try {
            val response = cartRemoteDataSources.getCart()
            emit(CommonState.Success<CartResponse>(data = response.data))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteCartItem(id: Int): Flow<CommonState<String>> = flow{
        try {
            val response = cartRemoteDataSources.deleteCartItem(id)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteCart(): Flow<CommonState<String>> = flow{
        try {
            val response = cartRemoteDataSources.clearCart()
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}