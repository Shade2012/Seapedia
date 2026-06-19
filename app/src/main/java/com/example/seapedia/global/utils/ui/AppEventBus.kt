package com.example.seapedia.global.utils.ui

import kotlinx.coroutines.flow.MutableSharedFlow

object AppEventBus {
    val events = MutableSharedFlow<UiEvent>()
}