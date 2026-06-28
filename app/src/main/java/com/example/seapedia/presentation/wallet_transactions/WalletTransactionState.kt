package com.example.seapedia.presentation.wallet_transactions

import com.example.seapedia.domain.entities.WalletTransaction

data class WalletTransactionState(
    val userId:Int = 0,
    val walletTransactions: List<WalletTransaction> = listOf(),
    val isLoading : Boolean = false,
    val error : String = "",
    val isRefreshing: Boolean = false
)