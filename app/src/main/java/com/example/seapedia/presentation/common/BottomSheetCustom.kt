package com.example.seapedia.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seapedia.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCustom(
    visible: Boolean,
    onDismiss:() -> Unit,
    content : @Composable ColumnScope.() -> Unit
) {
    if(!visible) return
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.InnerPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
            content = content
        )
    }
}