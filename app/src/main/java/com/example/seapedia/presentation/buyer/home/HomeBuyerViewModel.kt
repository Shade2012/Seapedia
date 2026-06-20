package com.example.seapedia.presentation.buyer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.auth.LogoutUseCase
import com.example.seapedia.global.utils.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBuyerViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _navigateToBuyer = MutableSharedFlow<Unit>()

    val navigateToBuyer = _navigateToBuyer.asSharedFlow()

    val state = sessionRepository.sessionState
    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.run()
            _navigateToBuyer.emit(Unit)
        }
    }
}