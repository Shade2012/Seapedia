package com.example.seapedia.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.seapedia.R
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.ErrorColor

@Composable
fun ConfirmationDialogCustom(
    visible: Boolean,
    title: String,
    message: String,
    confirmText:String = "Confirm",
    cancelText: String = "Cancel",

    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.CardCorner)
        ) {
            Column(
                modifier = Modifier.padding(Dimens.InnerPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.DialogSpacePadding)
            ) {
                ImageCustom(
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    contentDescription = "Image Confirmation",
                    painter = painterResource(R.drawable.confirmation)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(
                            text = cancelText,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ErrorColor
                            )
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(Dimens.SpacePadding)
                    )
                    ButtonCustom(
                        enabled = true,
                        title = confirmText,
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        isNotLoading = true
                    )
                }
            }
        }
    }
}