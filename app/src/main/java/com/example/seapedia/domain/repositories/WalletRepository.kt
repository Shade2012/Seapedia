package com.example.seapedia.domain.repositories

import com.example.seapedia.domain.entities.Wallet
import com.example.seapedia.domain.entities.WalletTransaction
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    suspend fun getBalance() : Flow<CommonState<Wallet>>

    suspend fun getWalletTransactions() : Flow<CommonState<List<WalletTransaction>>>
}