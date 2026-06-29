package com.example.seapedia.domain.usecases.carts

import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.domain.repositories.CartRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun run(): Flow<CommonState<String>>{
        return cartRepository.deleteCart()
    }
}