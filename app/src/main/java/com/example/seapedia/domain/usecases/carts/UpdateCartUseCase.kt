package com.example.seapedia.domain.usecases.carts

import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.domain.repositories.CartRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun run(id:Int, bodySend: CartItemBodySend): Flow<CommonState<String>>{
        return cartRepository.updateCartItem(id,bodySend)
    }
}