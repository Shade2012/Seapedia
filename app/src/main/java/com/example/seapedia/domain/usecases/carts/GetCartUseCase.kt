package com.example.seapedia.domain.usecases.carts

import com.example.seapedia.data.remote.body.BuyerPhoneNumberBody
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.domain.repositories.BuyerRepository
import com.example.seapedia.domain.repositories.CartRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun run(): Flow<CommonState<CartResponse>>{
        return cartRepository.getCart()
    }
}