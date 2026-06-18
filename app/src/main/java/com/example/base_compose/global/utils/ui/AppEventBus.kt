package com.example.base_compose.global.utils.ui

import kotlinx.coroutines.flow.MutableSharedFlow

object AppEventBus {
    val events = MutableSharedFlow<UiEvent>()
}