package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.wallet.WalletResponse
import com.example.seapedia.data.remote.responses.wallet.WalletTransactionResponse
import com.example.seapedia.data.remote.services.WalletService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRemoteDataSources @Inject constructor(
    private val walletService: WalletService
) {
//    suspend fun getProfile(): BaseResponse<ProfileResponse> = userService.getProfile()
    suspend fun getWallet(): BaseResponse<WalletResponse> = walletService.getBalance()
    suspend fun getWalletTransaction(): BaseResponse<List<WalletTransactionResponse>> = walletService.getWalletTransactions()
}