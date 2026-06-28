package com.example.seapedia.domain.usecases.order

import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.OrderHistory
import com.example.seapedia.domain.repositories.OrderRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetOrderHistoriesUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend fun run(id: Int): Flow<CommonState<List<OrderHistory>>>{
        return orderRepository.getOrderHistory(id)
    }
}