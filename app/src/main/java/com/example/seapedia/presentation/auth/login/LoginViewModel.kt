package com.example.seapedia.presentation.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.auth.LoginUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.EmailSupportingText
import com.example.seapedia.global.utils.PasswordSupportingText
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
): ViewModel() {
    private val _state : MutableStateFlow<LoginState> = MutableStateFlow<LoginState>(LoginState())
    val state = _state.asStateFlow()

    fun testSnackbar(){
        viewModelScope.launch {
            AppEventBus.events.emit(
                UiEvent.ShowSnackbar(
                    CustomSnackbarVisuals(message = "Test Snackbar", type = SnackbarType.SUCCESS)
                )
            )
        }
    }

    fun login(){
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.run(email = state.value.email, password = state.value.password).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(error = result.message, loading = false)
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Login Gagal, ${result.message}",
                                type = SnackbarType.ERROR
                            ))
                        )
                    }

                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(loading = true)
                        }
                    }

                    is CommonState.Success<*> -> {
                        updateState {
                            copy(loading = false)
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Login Success",
                                type = SnackbarType.SUCCESS
                            ))
                        )

                        Log.d("LoginViewModel","Success dapat token ${result.data}")
                    }
                }
            }

        }
    }

    fun setLoading(){
        Log.d("LoginViewModelTest",
            "Password Error : ${state.value.passwordError}\nEmail Error : ${state.value.emailError}\nPassword : ${state.value.password}\nEmail : ${state.value.email}")
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

    private fun updateState(
        update: LoginState.() -> LoginState
    ) {
        _state.update {
            it.update()
        }
    }
}