package com.example.seapedia.domain.usecases.order

import com.example.seapedia.data.remote.query.AllOrderQuery
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.repositories.OrderRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend fun run(queries: AllOrderQuery): Flow<CommonState<List<Order>>> {
        return orderRepository.getAllOrder(queries)
    }
}