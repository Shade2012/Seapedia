package com.example.seapedia.mapper.order

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.order.OrderHistoryResponse
import com.example.seapedia.domain.entities.OrderHistory
import com.example.seapedia.global.utils.Mapper
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class OrderHistoryListMapper : Mapper<BaseResponse<List<OrderHistoryResponse>>, List<OrderHistory>>{
    override fun mapFromResponse(type: BaseResponse<List<OrderHistoryResponse>>): List<OrderHistory> {
        val mapper = OrderHistoryRawMapper()
        return type.data.map {
            mapper.mapFromResponse(it)
        }
    }
}

class OrderHistoryRawMapper : Mapper<OrderHistoryResponse, OrderHistory> {
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: OrderHistoryResponse): OrderHistory {
        return OrderHistory(
            id = type.id,
            status = type.status,
            createdAt = Instant.parse(type.createdAt)
        )
    }
}