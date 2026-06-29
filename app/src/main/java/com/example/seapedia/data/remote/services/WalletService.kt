package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.TopUpBody
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.wallet.WalletResponse
import com.example.seapedia.data.remote.responses.wallet.WalletTransactionResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface WalletService {
    @GET(NetworkConstant.WALLET)
    suspend fun getBalance(): BaseResponse<WalletResponse>
    @GET("${NetworkConstant.WALLET_TRANSACTION}/revenue")
    suspend fun getRevenue(): BaseResponse<Int>
    @GET(NetworkConstant.WALLET_TRANSACTION)
    suspend fun getWalletTransactions(): BaseResponse<List<WalletTransactionResponse>>
    @POST(NetworkConstant.WALLET_TRANSACTION)
    suspend fun topUp(
        @Body body: TopUpBody
    ): BaseMessage

}