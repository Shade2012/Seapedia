package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.body.order.OrderBody
import com.example.seapedia.data.remote.query.AllOrderQuery
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.order.OrderPreviewResponse
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.OrderHistory
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getAllOrder(queries: AllOrderQuery): Flow<CommonState<List<Order>>>
    suspend fun getDetailOrder(id: Int): Flow<CommonState<Order>>
    suspend fun getOrderHistory(id: Int): Flow<CommonState<List<OrderHistory>>>
    suspend fun updateOrderStatus(id: Int): Flow<CommonState<String>>

    suspend fun getPreview(body: OrderBody): Flow<CommonState<OrderPreviewResponse>>

    suspend fun checkout(body: OrderBody): Flow<CommonState<String>>
}