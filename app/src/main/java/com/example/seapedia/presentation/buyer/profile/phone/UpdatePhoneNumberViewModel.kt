package com.example.seapedia.presentation.buyer.profile.phone

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.BuyerPhoneNumberBody
import com.example.seapedia.domain.usecases.buyer.UpdateBuyerUseCase
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.session.SessionRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePhoneNumberViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateBuyerUseCase: UpdateBuyerUseCase,
    private val sessionRepository: SessionRepository
) : ViewModel(){
    private val phoneNumberNew: String = checkNotNull(savedStateHandle[BuyerRoute.ProfileBuyerUpdatePhoneNumber.PHONE_NUMBER])
    val sessionState = sessionRepository.sessionState
    private val _navigateToProfile = MutableSharedFlow<Unit>()
    val navigateToProfile = _navigateToProfile

    private val _state = MutableStateFlow(UpdatePhoneNumberState())
    val state =_state.asStateFlow()

    init {
        updateState {
            copy(
                phoneNumber = phoneNumberNew,
                isLoading = false
            )
        }
    }

    fun updatePhoneNumber(){
        viewModelScope.launch {
            updateBuyerUseCase.run(
                BuyerPhoneNumberBody(
                    phoneNumber = state.value.phoneNumber
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
                        Log.d("Error VM",result.message)
                        updateState {
                            copy(
                                isLoading = false,
                                error = result.message
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
                        _navigateToProfile.emit(Unit)
                    }
                }
            }
        }
    }
    fun onPhoneNumberChange(value:String){
        updateState {
            copy(phoneNumber = value)
        }
    }

    private fun updateState(
        update: UpdatePhoneNumberState.() -> UpdatePhoneNumberState
    ){
        _state.update {
            it.update()
        }
    }
}