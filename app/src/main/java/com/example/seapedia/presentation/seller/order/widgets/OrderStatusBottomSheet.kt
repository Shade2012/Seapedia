package com.example.seapedia.presentation.seller.order.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seapedia.presentation.common.BottomSheetCustom
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun OrderStatusBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    BottomSheetCustom(
        visible = isVisible,
        onDismiss = onDismiss,
        content = {
            Text("Proccess Order", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
            ) {
                ButtonCustom(
                    modifier = Modifier.weight(1f),
                    enabled = true,
                    isNotLoading = true,
                    title = "Confirm",
                    onClick = {
                        onConfirm()
                    }
                )
            }

        }
    )
}