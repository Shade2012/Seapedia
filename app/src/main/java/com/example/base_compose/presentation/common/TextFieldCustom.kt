package com.example.base_compose.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.example.base_compose.global.utils.BaseSupportingText
import com.example.base_compose.ui.theme.Black
import com.example.base_compose.ui.theme.Dimens
import com.example.base_compose.ui.theme.Grey

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextFieldCustom(
    modifier: Modifier = Modifier,
    text: String,
    title: String,
    hint: String,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: BaseSupportingText? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChange: (String) -> Unit
) {
    val focusManager : FocusManager = LocalFocusManager.current
    Column {
        Text(title)
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            isError = isError,
            visualTransformation = visualTransformation,
            value = text,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = Black
            ),
            placeholder = {
                Text(hint, style = MaterialTheme.typography.bodyMedium.copy(
                    color = Black
                ))
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = Grey.copy(alpha = 100F),
                disabledTextColor = Black.copy(alpha = 0.38f),
                cursorColor = Black,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                focusedBorderColor = MaterialTheme.colorScheme.surface,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary

            ),

            onValueChange = onTextChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )
        Spacer(modifier.padding(Dimens.SpacePadding))
        supportingText?.SupportText(text)
    }
}