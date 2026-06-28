package com.example.seapedia.presentation.wallet_transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.auth.GetUserIdUseCase
import com.example.seapedia.domain.usecases.wallet.GetWalletTransactionsUseCase
import com.example.seapedia.global.utils.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletTransactionsViewModel @Inject constructor(
    private val getWalletTransactionsUseCase: GetWalletTransactionsUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
): ViewModel(){
    private val _state = MutableStateFlow(WalletTransactionState())
    val state = _state.asStateFlow()

    init {
        onInitLoadData()
    }

    fun onRefresh(){
        onLoadData(true)
    }
    private fun onInitLoadData(){
        onLoadData(false)
    }

    private fun onLoadData(isRefreshing : Boolean){
        viewModelScope.launch {
            try {
                updateState {
                    copy(
                        isRefreshing = isRefreshing,
                        isLoading = true
                    )
                }
                coroutineScope {
                    launch {
                        val userId = getUserIdUseCase.run().first()
                        updateState {
                            copy(userId = userId ?: 0)
                        }
                    }
                    launch {
                        getWalletTransactionsUseCase.run().collect {
                                result ->
                            when(result){
                                is CommonState.Error<*> -> {
                                    updateState {
                                        copy(error = result.message)
                                    }
                                }
                                is CommonState.Loading<*> -> {}
                                is CommonState.Success -> {
                                    updateState {
                                        copy(
                                            walletTransactions = result.data
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } finally {
                updateState {
                    copy(
                        isRefreshing = false,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun updateState(
        updateState: WalletTransactionState.() -> WalletTransactionState
    ){
        _state.update {
            it.updateState()
        }
    }
}