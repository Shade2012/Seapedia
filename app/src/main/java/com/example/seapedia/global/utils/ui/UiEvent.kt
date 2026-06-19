package com.example.seapedia.global.utils.ui

sealed interface UiEvent {
    data class ShowSnackbar(
        val data : CustomSnackbarVisuals
    ) : UiEvent
}