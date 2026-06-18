package com.example.base_compose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

//@Composable
//val errorsTextStyle: TextStyle = typography.bodySmall.copy(
//    color = colorScheme.error
//)
val Typography.error: TextStyle
    @Composable
    get() = typography.bodySmall.copy(
        color = ErrorColor
    )