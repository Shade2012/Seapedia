package com.example.seapedia.domain.usecases.wallet

import com.example.seapedia.domain.entities.WalletTransaction
import com.example.seapedia.domain.repositories.WalletRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWalletTransactionsUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {
    suspend fun run(): Flow<CommonState<List<WalletTransaction>>>{
        return walletRepository.getWalletTransactions()
    }
}