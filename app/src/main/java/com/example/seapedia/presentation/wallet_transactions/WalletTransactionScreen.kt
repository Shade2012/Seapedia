package com.example.seapedia.presentation.wallet_transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.RefreshCommon
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.wallet_transactions.shimmer.WalletTransactionSectionShimmer
import com.example.seapedia.presentation.wallet_transactions.widgets.WalletTransactionSection
import com.example.seapedia.ui.theme.Dimens

@Composable
fun WalletTransactionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: WalletTransactionsViewModel = hiltViewModel<WalletTransactionsViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    RefreshCommon(
        modifier = Modifier.fillMaxSize(),
        refreshing = state.isRefreshing,
        onRefresh = viewModel::onRefresh,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(Dimens.InnerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopAppBarCustom(
                title = "Wallet Transactions",
                onBackClick = {
                    navController.popBackStack()
                }
            )

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        WalletTransactionSectionShimmer()
                    }
                }

                state.error.isNotEmpty() -> {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        FailedCommonCustom(text = state.error)
                    }
                }

                else -> {
                    WalletTransactionSection(
                        modifier = Modifier.weight(1f),
                        userId = state.userId,
                        transactions = state.walletTransactions
                    )
                }
            }
        }
    }
}