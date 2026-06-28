package com.example.seapedia.presentation.buyer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.buyer.CheckValidBuyerUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.global.utils.session.SessionRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainBuyerViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val checkValidBuyerUseCase: CheckValidBuyerUseCase
): ViewModel() {
    val state = sessionRepository.sessionState
    private val _showInvalidStoreDialog = MutableStateFlow(false)
    val showInvalidStoreDialog = _showInvalidStoreDialog.asStateFlow()

    init {
        if(state.value.role == UserRole.Buyer)
            checkValidBuyer()
    }
    private fun checkValidBuyer(){
        viewModelScope.launch {
            checkValidBuyerUseCase.run().collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    message = "Failed to check validity",
                                    type = SnackbarType.ERROR
                                )
                            )
                        )
                    }
                    is CommonState.Loading<*> -> {}
                    is CommonState.Success -> {
                        _showInvalidStoreDialog.emit(result.data)
                        sessionRepository.setValid(result.data)
                    }
                }

            }
        }
    }
    fun dismissDialog() {
        _showInvalidStoreDialog.value = false
    }
}