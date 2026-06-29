package com.example.seapedia.domain.usecases.order

import com.example.seapedia.data.remote.body.order.OrderBody
import com.example.seapedia.data.remote.responses.order.OrderPreviewResponse
import com.example.seapedia.domain.repositories.OrderRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreviewUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend fun run(body: OrderBody): Flow<CommonState<OrderPreviewResponse>>{
        return orderRepository.getPreview(body)
    }
}