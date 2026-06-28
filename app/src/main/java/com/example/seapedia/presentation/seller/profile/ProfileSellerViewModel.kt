package com.example.seapedia.presentation.seller.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.entities.UserProfile
import com.example.seapedia.domain.usecases.auth.LogoutUseCase
import com.example.seapedia.domain.usecases.user.GetProfileUseCase
import com.example.seapedia.global.utils.CommonState
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
class ProfileSellerViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase

) : ViewModel() {
    private val _state = MutableStateFlow<CommonState<UserProfile>>(CommonState.Loading())
    private val _navigateToLogin = MutableSharedFlow<Unit>()
    val navigateToAuth = _navigateToLogin.asSharedFlow()
    val state = _state.asStateFlow()

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
                    is CommonState.Success<UserProfile> -> {
                        _state.emit(result)
                    }
                }
            }
        }
    }
}
