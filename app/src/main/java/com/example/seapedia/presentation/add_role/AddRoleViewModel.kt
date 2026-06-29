package com.example.seapedia.presentation.add_role

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.user.AddUserRoleUseCase
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRoleViewModel @Inject constructor(
    val sessionRepository: SessionRepository,
    private val addUserRoleUseCase: AddUserRoleUseCase
) : ViewModel(){
    private val _state = MutableStateFlow<AddRoleState>(AddRoleState())
    val state = _state.asStateFlow()
    private val _isSuccess = MutableSharedFlow<Unit>()
    val isSuccess = _isSuccess.asSharedFlow()
    val sessionState = sessionRepository.sessionState

    init {
        onInit()
    }

    fun onInit(){
        updateState {
            copy(
                role = sessionState.value.role
            )
        }
    }

    fun addRole(){
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true
                )
            }

            addUserRoleUseCase.run(state.value.role!!).collect {
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
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                isLoading = true
                            )
                        }
                    }
                    is CommonState.Success<*> -> {
                        _isSuccess.emit(Unit)
                    }
                }
            }
            updateState {
                copy(
                    isLoading = false
                )
            }
        }
    }

    fun onSelectedRole(value: UserRole){
        updateState {
            copy(
                role = value
            )
        }
    }

    private fun updateState(
        updateState: AddRoleState.() -> AddRoleState
    ){
        _state.update {
            it.updateState()
        }
    }
}