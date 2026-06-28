package com.example.seapedia.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.auth.LoginUseCase
import com.example.seapedia.domain.usecases.auth.SetAccessTokenUseCase
import com.example.seapedia.domain.usecases.auth.SetRoleUseCase
import com.example.seapedia.domain.usecases.auth.SetUserIdUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.EmailSupportingText
import com.example.seapedia.global.utils.PasswordSupportingText
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.global.utils.auth.decodeJwt
import com.example.seapedia.global.utils.session.SessionRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val loginUseCase: LoginUseCase,
    private val setRoleUseCase: SetRoleUseCase,
    private val setUserIdUseCase: SetUserIdUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase
): ViewModel() {
    private val _state : MutableStateFlow<LoginState> = MutableStateFlow<LoginState>(LoginState())
    val state = _state.asStateFlow()
    private val _navigateToSplash = MutableSharedFlow<Unit>()
    val navigateToSplash = _navigateToSplash.asSharedFlow()

    fun login(){
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.run(email = state.value.email, password = state.value.password, role = state.value.selectedRole!!).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(error = result.message, isLoading = false)
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Login Failed, ${result.message}",
                                type = SnackbarType.ERROR
                            ))
                        )
                    }

                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(isLoading = true)
                        }
                    }

                    is CommonState.Success<String> -> {
                        updateState {
                            copy(isLoading = false)
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Login Success",
                                type = SnackbarType.SUCCESS
                            ))
                        )
                        val payload = decodeJwt(result.data)
                        setUserIdUseCase.run(payload.sub)
                        setRoleUseCase.run(state.value.selectedRole!!)
                        setAccessTokenUseCase.run(result.data)
                        sessionRepository.login(
                            token = result.data,
                            isLoggedIn = true,
                            role = state.value.selectedRole,
                        )
                        _navigateToSplash.emit(Unit)
                    }
                }
            }

        }
    }
    fun onEmailChange(value: String){
        updateState {
            copy(
                email = value,
                emailError = !EmailSupportingText.validate(value)
            )
        }
    }
    fun onPasswordChange(value : String){
        updateState {
            copy(
                password = value,
                passwordError = !PasswordSupportingText.validate(value)
            )
        }
    }
    fun onRoleChange(value : UserRole){
        updateState {
            copy(
                selectedRole = value,
            )
        }
    }
    fun onPasswordVisible(){
        updateState {
            copy(
                isPasswordVisible = !isPasswordVisible
            )
        }
    }
    fun onContinueAsGuest(){
        viewModelScope.launch(Dispatchers.IO){
            setRoleUseCase.run(UserRole.Guest)
            sessionRepository.login(
                isLoggedIn = true,
                role = UserRole.Guest
            )
            _navigateToSplash.emit(Unit)
        }
    }
    private fun updateState(
        update: LoginState.() -> LoginState
    ) {
        _state.update {
            it.update()
        }
    }
}