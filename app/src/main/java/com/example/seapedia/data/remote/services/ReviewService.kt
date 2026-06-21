package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.ReviewBody
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ReviewResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ReviewService {
    @GET(NetworkConstant.REVIEWS)
    suspend fun getAllReview(@QueryMap queries: Map<String, String>): BaseResponse<List<ReviewResponse>>

    @POST(NetworkConstant.REVIEWS)
    suspend fun createReview(
        @Body body: ReviewBody
    ): BaseResponse<ReviewResponse>
}