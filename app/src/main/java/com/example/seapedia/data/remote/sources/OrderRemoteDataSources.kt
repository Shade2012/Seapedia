package com.example.seapedia.data.remote.sources


import com.example.seapedia.data.remote.body.OrderStatusBody
import com.example.seapedia.data.remote.query.AllOrderQuery
import com.example.seapedia.data.remote.query.toMap
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.order.OrderHistoryResponse
import com.example.seapedia.data.remote.responses.order.OrderResponse
import com.example.seapedia.data.remote.services.OrderService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class OrderRemoteDataSources @Inject constructor(
    private val orderService: OrderService,
) {
    suspend fun getAllOrder(queries: AllOrderQuery): BaseResponse<List<OrderResponse>> = orderService.getAllOrders(queries.toMap())
    suspend fun getDetailOrder(id: Int): BaseResponse<OrderResponse> = orderService.getDetailOrder(id)
    suspend fun getOrderHistory(id: Int): BaseResponse<List<OrderHistoryResponse>> = orderService.getOrderHistories(id)
    suspend fun updateOrderHistory(id: Int, body: OrderStatusBody): BaseMessage = orderService.updateOrderStatus(id,body)
}