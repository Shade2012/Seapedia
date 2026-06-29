package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.sources.OrderRemoteDataSources
import com.example.seapedia.data.remote.body.OrderStatusBody
import com.example.seapedia.data.remote.body.order.OrderBody
import com.example.seapedia.data.remote.query.AllOrderQuery
import com.example.seapedia.data.remote.responses.order.OrderPreviewResponse
import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.OrderHistory
import com.example.seapedia.domain.repositories.OrderRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.order.OrderHistoryListMapper
import com.example.seapedia.mapper.order.OrderMapper
import com.example.seapedia.mapper.order.OrderMapperList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class OrderRepositoryImpl @Inject constructor(
    private val orderRemoteDataSources: OrderRemoteDataSources,
) : OrderRepository {
    override suspend fun getAllOrder(queries: AllOrderQuery): Flow<CommonState<List<Order>>> = flow{
        try {
            val response = orderRemoteDataSources.getAllOrder(queries)
            val orders = OrderMapperList().mapFromResponse(response)
            emit(CommonState.Success<List<Order>>(data = orders))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getDetailOrder(id: Int): Flow<CommonState<Order>> = flow{
        emit(CommonState.Loading())
        try {
            val response = orderRemoteDataSources.getDetailOrder(id)
            val order = OrderMapper().mapFromResponse(response)
            emit(CommonState.Success<Order>(data = order))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getOrderHistory(id: Int): Flow<CommonState<List<OrderHistory>>> = flow{
        emit(CommonState.Loading())
        try {
            val response = orderRemoteDataSources.getOrderHistory(id)
            val histories = OrderHistoryListMapper().mapFromResponse(response)
            emit(CommonState.Success<List<OrderHistory>>(data = histories))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        } catch (e: CancellationException) {
            throw e
        }
    }

    override suspend fun updateOrderStatus(id: Int): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = orderRemoteDataSources.updateOrderHistory(id, OrderStatusBody(
                orderStatus = OrderStatus.WAITING_DRIVER
            ))
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        } catch (e: CancellationException) {
            throw e
        }
    }

    override suspend fun getPreview(body: OrderBody): Flow<CommonState<OrderPreviewResponse>> = flow{
        emit(CommonState.Loading())
        try {
            val response = orderRemoteDataSources.getPreview(body)
            emit(CommonState.Success<OrderPreviewResponse>(data = response.data))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        } catch (e: CancellationException) {
            throw e
        }
    }

    override suspend fun checkout(body: OrderBody): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = orderRemoteDataSources.checkout(body)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        } catch (e: CancellationException) {
            throw e
        }
    }
}