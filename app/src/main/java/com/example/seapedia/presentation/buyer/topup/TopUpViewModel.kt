package com.example.seapedia.presentation.buyer.topup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.TopUpBody
import com.example.seapedia.domain.usecases.wallet.TopUpUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopUpViewModel @Inject constructor(
    private val topUpUseCase: TopUpUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<TopUpState>(TopUpState())
    val state = _state.asStateFlow()

    private val _isSuccess = MutableSharedFlow<Unit>()
    val isSuccess = _isSuccess.asSharedFlow()

    fun onTopUp(){
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true
                )
            }
            topUpUseCase.run(
                TopUpBody(
                    amount = state.value.amount.toIntOrNull() ?: 0
                )
            ).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.ERROR,
                                    message = result.message
                                )
                            )
                        )
                        updateState {
                            copy(
                                isLoading = false
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                isLoading = true
                            )
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState {
                            copy(
                                isLoading = false
                            )
                        }
                        _isSuccess.emit(Unit)
                    }
                }

            }
        }
    }

    fun onAmountChange(amount: String){
        updateState {
            copy(
                amount = amount
            )
        }
    }

    private fun updateState(
        updateState: TopUpState.() -> TopUpState
    ){
        _state.update {
            it.updateState()
        }
    }
}