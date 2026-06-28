package com.example.seapedia.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.seapedia.R
import com.example.seapedia.ui.theme.Dimens


@Composable
fun EmptyCommonCustom(
    modifier: Modifier = Modifier,
    imageId:Int? = null,
    text: String = "Empty"
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(imageId ?: R.drawable.empty),
            contentDescription = "Failed Image"
        )
        Spacer(modifier.padding(vertical = Dimens.TopPadding))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}