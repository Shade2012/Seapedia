package com.example.seapedia.domain.usecases.wallet

import com.example.seapedia.data.remote.body.TopUpBody
import com.example.seapedia.domain.entities.Wallet
import com.example.seapedia.domain.repositories.WalletRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TopUpUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {
    suspend fun run(body: TopUpBody): Flow<CommonState<String>>{
        return walletRepository.topUp(body)
    }
}