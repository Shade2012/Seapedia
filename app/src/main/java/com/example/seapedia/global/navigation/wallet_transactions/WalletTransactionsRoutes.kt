package com.example.seapedia.global.navigation.wallet_transactions

sealed class WalletTransactionsRoute(val route: String,val name: String){
    object WalletTransactionAll : WalletTransactionsRoute("wallet_transactions_all","wallet_transactions_all_screen")
}