package com.example.seapedia.presentation.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.seapedia.R

@Composable
fun ImageCustom(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    contentDescription: String
) {
    AsyncImage(
        model = ImageRequest.Builder(
            LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}