package com.example.seapedia.presentation.buyer.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.seapedia.presentation.common.ButtonCustom

@Composable
fun AddToCartSection(
    modifier: Modifier = Modifier,
    quantity: Int = 0,
    onClick: () -> Unit
) {
    val condition = quantity < 1
    val text = if(condition) "Add to Cart" else "$quantity Items"
    val titleStyle = if(condition) null else MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.SemiBold
    )
    ButtonCustom(
        enabled = true,
        title = text,
        titleStyle = titleStyle,
        onClick = onClick,
        isNotLoading = true
    )
}