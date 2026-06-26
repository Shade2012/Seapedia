package com.example.seapedia.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
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
import com.example.seapedia.global.utils.BaseSupportingText
import com.example.seapedia.ui.theme.Black
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.Grey
import com.example.seapedia.ui.theme.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextFieldCustom(
    modifier: Modifier = Modifier,
    text: String,
    title: String? = null,
    hint: String,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    enabled: Boolean = true,
    isError: Boolean = false,
    minLines:Int = 1,
    supportingText: BaseSupportingText? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon : @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTextChange: (String) -> Unit
) {
    val focusManager : FocusManager = LocalFocusManager.current
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        if(title != null)
            Text(title, style = MaterialTheme.typography.labelMedium)
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            enabled = enabled,
            isError = isError,
            minLines = minLines,
            visualTransformation = visualTransformation,
            value = text,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = White
            ),
            placeholder = {
                Text(hint, style = MaterialTheme.typography.bodyMedium.copy(
                    color = White
                ))
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledContainerColor = Grey.copy(alpha = 0.5f),
                disabledTextColor = Black.copy(alpha = 0.38f),
                cursorColor = White,
                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.tertiary,
                    backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)
                ),
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
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )
        Spacer(modifier.padding(Dimens.SpacePadding))
        supportingText?.SupportText(text)
    }
}