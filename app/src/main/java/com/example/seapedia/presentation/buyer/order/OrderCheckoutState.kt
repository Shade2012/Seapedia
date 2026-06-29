package com.example.seapedia.presentation.buyer.order

import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.data.remote.responses.order.DeliveryMethod
import com.example.seapedia.data.remote.responses.order.OrderPreviewResponse
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.global.utils.CommonState

data class OrderCheckoutState(
    val listAddress: CommonState<List<Address>> = CommonState.Loading(),
    val cart: CommonState<CartResponse> = CommonState.Loading(),
    val selectedAddress: Address? = null,
    val orderPreviewResponse: CommonState<OrderPreviewResponse> = CommonState.Loading(),

    val selectedDeliveryMethod: DeliveryMethod = DeliveryMethod.REGULAR,
    val voucherCode : String? = null,
    val isRefreshing : Boolean = false,
    val isLoading : Boolean = false,
    val isConfirmLoading : Boolean = false
)