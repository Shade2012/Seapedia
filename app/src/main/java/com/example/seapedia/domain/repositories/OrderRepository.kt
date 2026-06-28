package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.query.AllOrderQuery
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.OrderHistory
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getAllOrder(queries: AllOrderQuery): Flow<CommonState<List<Order>>>
    suspend fun getDetailOrder(id: Int): Flow<CommonState<Order>>
    suspend fun getOrderHistory(id: Int): Flow<CommonState<List<OrderHistory>>>
    suspend fun updateOrderStatus(id: Int): Flow<CommonState<String>>
}