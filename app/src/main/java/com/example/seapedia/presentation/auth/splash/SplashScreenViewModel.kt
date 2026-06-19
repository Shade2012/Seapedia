package com.example.seapedia.presentation.auth.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.auth.GetAccessTokenUseCase
import com.example.seapedia.domain.usecases.auth.GetRoleUseCase
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.global.utils.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val getRoleUseCase: GetRoleUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
) : ViewModel(){
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    val state = sessionRepository.sessionState
    init {
        viewModelScope.launch {
            delay(500)
            val accessToken = getAccessTokenUseCase.run().first()
            val role = UserRole.entries.find { it.name ==  getRoleUseCase.run().first() }
            Log.d("Splash ViewModel Role",role?.name ?: "")
            Log.d("Splash ViewModel Token",accessToken ?: "")
            _isLoading.value = false
            sessionRepository.login(
                accessToken,
                role,
                isLoggedIn = role != null,
//                null
            )
        }
    }
}