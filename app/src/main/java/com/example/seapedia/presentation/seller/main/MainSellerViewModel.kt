package com.example.seapedia.presentation.seller.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.store.GetCheckValidationStoreUseCase
import com.example.seapedia.global.utils.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSellerViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val getCheckValidationStoreUseCase: GetCheckValidationStoreUseCase
): ViewModel() {
    private val _showInvalidStoreDialog = MutableStateFlow(false)
    val showInvalidStoreDialog = _showInvalidStoreDialog.asStateFlow()

    private val _validState = MutableStateFlow(false)
    val validState = _validState.asStateFlow()
    val state = sessionRepository.sessionState

    init {
        getValid()
    }
    fun getValid(){
        viewModelScope.launch {
            val isValid = getCheckValidationStoreUseCase.run()
            if (!isValid) {
                _showInvalidStoreDialog.value = true
            }
            _validState.value = isValid
        }
    }
    fun showDialog(){
        _showInvalidStoreDialog.value = true
    }
    fun dismissDialog() {
        _showInvalidStoreDialog.value = false
    }
}