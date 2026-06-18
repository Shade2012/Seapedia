package com.example.base_compose.global.utils.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.ui.graphics.Color

enum class SnackbarType(
    val containerColor: Color,
    val contentColor: Color = Color.White,
) {
    SUCCESS(containerColor = Color(0xFF4CAF50)),
    ERROR(containerColor = Color(0xFFF44336)),
    WARNING(containerColor = Color(0xFFFF9800)),
    DEFAULT(containerColor = Color.DarkGray);
}

data class CustomSnackbarVisuals(
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val message: String,
    override val withDismissAction: Boolean = false,
    val type: SnackbarType = SnackbarType.DEFAULT
) : SnackbarVisuals
