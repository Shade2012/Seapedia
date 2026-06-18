package com.example.base_compose.global.utils.ui

sealed interface UiEvent {
    data class ShowSnackbar(
        val data : CustomSnackbarVisuals
    ) : UiEvent
}