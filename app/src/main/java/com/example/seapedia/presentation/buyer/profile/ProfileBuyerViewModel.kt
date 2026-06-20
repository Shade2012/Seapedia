package com.example.seapedia.presentation.buyer.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.entities.UserProfileEntity
import com.example.seapedia.domain.usecases.auth.LogoutUseCase
import com.example.seapedia.domain.usecases.user.GetProfileUseCase
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileBuyerViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val sessionRepository: SessionRepository,
    private val logoutUseCase: LogoutUseCase

) : ViewModel() {
    private val _state = MutableStateFlow<CommonState<UserProfileEntity>>(CommonState.Loading())
    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToBuyer = _navigateToLogin.asSharedFlow()
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
                getProfileUseCase.run().collect {
                        result ->
                    when(result){
                        is CommonState.Error<*> -> {
                            _state.emit(CommonState.Error(
                                message = result.message
                            ))
                            AppEventBus.events.emit(
                                UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                    message = "Failed get profile, ${result.message}",
                                    type = SnackbarType.ERROR
                                ))
                            )
                        }
                        is CommonState.Loading<*> -> {
                            _state.emit(CommonState.Loading())
                        }
                        is CommonState.Success<UserProfileEntity> -> {
                            _state.emit(result)
                        }
                    }
                }
            }
        }else{
            viewModelScope.launch {
                _state.emit(CommonState.Success(UserProfileEntity.GUEST_PROFILE_ENTITY))
            }
        }
    }
}