package com.example.seapedia.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.seapedia.R

@Composable
fun ImageCustom(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    painter: Painter? = null,
    vector: ImageVector? = null,
    contentDescription: String
) {

    when {
        imageUrl != null -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
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

        painter != null -> {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
        vector != null -> {
            Image(
                imageVector = vector,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop,
            )
        }

        else -> {
            Image(
                painter = painterResource(R.drawable.default_image),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
    }
}