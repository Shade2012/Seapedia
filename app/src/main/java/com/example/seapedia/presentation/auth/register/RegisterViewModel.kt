package com.example.seapedia.presentation.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.RegisterBody
import com.example.seapedia.domain.usecases.auth.RegisterUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.EmailSupportingText
import com.example.seapedia.global.utils.PasswordSupportingText
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _navigateToLogin: MutableSharedFlow<Unit> = MutableSharedFlow<Unit>()
    val navigateToLogin = _navigateToLogin

    fun register(){
        viewModelScope.launch(Dispatchers.IO){
            registerUseCase.run(
                RegisterBody(
                    fullName = state.value.fullName,
                    email = state.value.email,
                    password = state.value.password,
                    role = state.value.selectedRole!!.name
                )
            ).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(error = result.message, loading = false)
                        }
                        Log.d("Register View Model",result.message)
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Register Failed, ${result.message}",
                                type = SnackbarType.ERROR
                            ))
                        )
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                loading = true
                            )
                        }
                    }
                    is CommonState.Success<String> -> {
                        updateState {
                            copy(loading = false)
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = result.data,
                                type = SnackbarType.SUCCESS
                            ))
                        )
                        _navigateToLogin.emit(Unit)
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
    fun onFullNameChange(value: String){
        updateState {
            copy(
                fullName = value,
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

    private fun updateState(
        update: RegisterState.() -> RegisterState
    ){
        _state.update {
            it.update()
        }
    }


}