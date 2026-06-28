package com.example.seapedia.presentation.buyer.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.entities.BuyerProfile
import com.example.seapedia.domain.entities.UserProfile
import com.example.seapedia.domain.usecases.auth.LogoutUseCase
import com.example.seapedia.domain.usecases.user.GetBuyerProfileUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.global.utils.session.SessionRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileBuyerViewModel @Inject constructor(
    private val getBuyerProfileUseCase: GetBuyerProfileUseCase,
    private val sessionRepository: SessionRepository,
    private val logoutUseCase: LogoutUseCase

) : ViewModel() {
    private val _state = MutableStateFlow<ProfileBuyerState>(ProfileBuyerState())
    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToAuth = _navigateToLogin.asSharedFlow()
    val state = _state.asStateFlow()
    val sessionState = sessionRepository.sessionState

    init {
        getProfile()
    }

    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.run()
            _navigateToLogin.emit(Unit)
        }
    }
    private fun getProfile(){
        if(sessionState.value.role != UserRole.Guest){
            viewModelScope.launch(Dispatchers.IO){
                delay(200)
                getBuyerProfileUseCase.run().collect {
                        result ->
                    when(result){
                        is CommonState.Error -> {
                            updateState {
                                copy(userProfile = CommonState.Error(result.message))
                            }
                            AppEventBus.events.emit(
                                UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                    message = "Failed get profile, ${result.message}",
                                    type = SnackbarType.ERROR
                                ))
                            )
                        }
                        is CommonState.Loading<*> -> {
                            updateState {
                                copy(userProfile = CommonState.Loading())
                            }
                        }
                        is CommonState.Success<BuyerProfile> -> {
                            updateState {
                                copy(
                                    userProfile = CommonState.Success(result.data.user),
                                    phoneNumber = result.data.phoneNumber ?: ""
                                )
                            }
                        }
                    }
                }
            }
        }else{
            viewModelScope.launch {
                updateState {
                    copy(userProfile = CommonState.Success(UserProfile.GUEST_PROFILE_ENTITY))
                }
            }
        }
    }
    private fun updateState(
        updateState: ProfileBuyerState.() -> ProfileBuyerState
    ){
        _state.update {
            it.updateState()
        }
    }
}