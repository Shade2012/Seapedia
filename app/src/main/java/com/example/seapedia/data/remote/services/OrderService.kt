package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.data.remote.body.OrderStatusBody
import com.example.seapedia.data.remote.body.ProductImageBody
import com.example.seapedia.data.remote.body.order.OrderBody
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.order.OrderHistoryResponse
import com.example.seapedia.data.remote.responses.order.OrderPreviewResponse
import com.example.seapedia.data.remote.responses.order.OrderResponse
import com.example.seapedia.data.remote.responses.product.ProductImageResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.data.remote.responses.product.SellerProductsResponse
import com.example.seapedia.global.networks.NetworkConstant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface OrderService {
    @GET(NetworkConstant.ORDERS)
    suspend fun getAllOrders(@QueryMap queries: Map<String, String>): BaseResponse<List<OrderResponse>>

    @GET("${NetworkConstant.ORDERS}/{id}")
    suspend fun getDetailOrder(@Path("id") id: Int): BaseResponse<OrderResponse>
    @GET("${NetworkConstant.ORDERS}/${NetworkConstant.HISTORY}/{id}")
    suspend fun getOrderHistories(@Path("id") id: Int): BaseResponse<List<OrderHistoryResponse>>

    @PATCH("${NetworkConstant.ORDERS}/{id}")
    suspend fun updateOrderStatus(
        @Path("id") id: Int,
        @Body body: OrderStatusBody
    ): BaseMessage

    @POST("${NetworkConstant.ORDERS}/preview")
    suspend fun getPreview(
        @Body body: OrderBody
    ): BaseResponse<OrderPreviewResponse>

    @POST(NetworkConstant.ORDERS)
    suspend fun checkout(
        @Body body: OrderBody
    ): BaseMessage
}