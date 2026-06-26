package com.example.seapedia.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White

@Composable
fun IconCustom(
    modifier: Modifier = Modifier,
    id: Int = 0,
    color: Color = White,
    contentDescription: String = "",
    icon: ImageVector? = null
) {
    Icon(
        modifier = modifier.size(
            Dimens.IconSize
        ),
        imageVector = icon ?: ImageVector.vectorResource(id),
        tint = color,
        contentDescription = contentDescription,

    )
}