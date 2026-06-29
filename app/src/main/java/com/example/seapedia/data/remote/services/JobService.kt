package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.OrderStatusBody
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.body.job.JobBody
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.job.JobDriverResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface JobService {
    @GET("${NetworkConstant.DRIVER}/${NetworkConstant.JOB}/available")
    suspend fun getAvailableJob() : BaseResponse<List<JobDriverResponse>>

    @GET("${NetworkConstant.DRIVER}/${NetworkConstant.JOB}/current")
    suspend fun getCurrentJob() : BaseResponse<List<JobDriverResponse>>

    @GET("${NetworkConstant.DRIVER}/${NetworkConstant.JOB}/history")
    suspend fun getHistoryJob() : BaseResponse<List<JobDriverResponse>>

    @GET("${NetworkConstant.DRIVER}/${NetworkConstant.JOB}/{id}")
    suspend fun getDetailJob(
        @Path("id") id: Int,
    ) : BaseResponse<JobDriverResponse>

    @POST("${NetworkConstant.DRIVER}/${NetworkConstant.JOB}")
    suspend fun takeJob(
        @Body body: JobBody
    ) : BaseMessage

    @POST("${NetworkConstant.DRIVER}/${NetworkConstant.JOB}/{id}")
    suspend fun confirmJob(
        @Path("id") id : Int
    ) : BaseMessage
}