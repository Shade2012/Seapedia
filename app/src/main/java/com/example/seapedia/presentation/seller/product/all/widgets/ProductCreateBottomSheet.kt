package com.example.seapedia.presentation.seller.product.all.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seapedia.presentation.common.BottomSheetCustom
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun ProductModalBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onNavigateUpdateProduct: () -> Unit,
    onNavigateUpdateProductImage: () -> Unit,
) {
    BottomSheetCustom(
        visible = isVisible,
        onDismiss = onDismiss,
        content = {
            Text("Edit Action")
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
            ) {
                ButtonCustom(
                    modifier = Modifier.weight(1f),
                    enabled = true,
                    isNotLoading = true,
                    title = "Edit Product",
                    onClick = {
                        onNavigateUpdateProduct()
                    }
                )
                ButtonCustom(
                    modifier = Modifier.weight(1f),
                    enabled = true,
                    isNotLoading = true,
                    title = "Edit Product Image",
                    onClick = {
                        onNavigateUpdateProductImage()
                    }
                )
            }

        }
    )
}