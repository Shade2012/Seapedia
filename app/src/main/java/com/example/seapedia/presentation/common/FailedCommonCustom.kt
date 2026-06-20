package com.example.seapedia.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.seapedia.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.seapedia.ui.theme.ErrorColor

@Composable
fun FailedCommonCustom(
    modifier: Modifier = Modifier,
    imageId:Int? = null,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(imageId ?: R.drawable.failed),
            contentDescription = "Failed Image"
        )
        Text(text, style = MaterialTheme.typography.bodyMedium.copy(
            color = ErrorColor
        ))
    }
}