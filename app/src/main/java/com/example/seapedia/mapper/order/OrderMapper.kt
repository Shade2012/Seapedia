package com.example.seapedia.mapper.order

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.order.OrderAddressResponse
import com.example.seapedia.data.remote.responses.order.OrderResponse
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.OrderAddress
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.mapper.StoreRawMapper
import com.example.seapedia.mapper.job.JobMapperRawToDriver
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

//GW CAPEKKK 01:03 AM, DARI PAGI KE PAGI
class OrderMapperList: Mapper<BaseResponse<List<OrderResponse>>, List<Order>>{
    override fun mapFromResponse(type: BaseResponse<List<OrderResponse>>): List<Order> {
        val values = type.data
        val mapper = OrderRawMapper()
        return values.map {
            mapper.mapFromResponse(it)
        }
    }

}
class OrderMapper : Mapper<BaseResponse<OrderResponse>, Order>{
    override fun mapFromResponse(type: BaseResponse<OrderResponse>): Order {
        val value = type.data
        return OrderRawMapper().mapFromResponse(value)
    }
}
class OrderRawMapper : Mapper<OrderResponse, Order>{
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: OrderResponse): Order {
        val item = type
        return Order(
            id = item.id,
            deliveryMethod = item.deliveryMethod,
            distanceJourneyKm = item.distanceJourneyKm,
            overdue = Instant.parse(item.overdue),
            deliveryFee = item.deliveryFee,
            taxFee = item.taxFee,
            subTotal = item.subTotal,
            totalFee = item.totalFee,
            voucherDiscount = item.voucherDiscount,
            status = item.status,
            createdAt = Instant.parse(item.createdAt),
            orderAddress = OrderAddressRawMapper().mapFromResponse(item.orderAddressResponse),
            store = StoreRawMapper().mapFromResponse(item.store),
            driver = type.jobResponse?.let {
                JobMapperRawToDriver().mapFromResponse(type.jobResponse)
            },
            orderItems = item.orderItemResponses.map {
                OrderItemMapper().mapFromResponse(it)
            }
        )
    }
}

class OrderAddressRawMapper : Mapper<OrderAddressResponse, OrderAddress>{
    override fun mapFromResponse(type: OrderAddressResponse): OrderAddress {
        return OrderAddress(
            id = type.id,
            receiverName = type.receiverName,
            receiverPhoneNumber = type.receiverPhoneNumber,
            receiverAddress = type.receiverAddress,
            latitude = type.latitude,
            longitude = type.longitude
        )
    }

}

