package com.example.seapedia.presentation.buyer.main

import androidx.lifecycle.ViewModel
import com.example.seapedia.global.utils.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainBuyerViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
): ViewModel() {
    val state = sessionRepository.sessionState
}