package com.example.seapedia.presentation.common

import androidx.compose.foundation.clickable
import com.example.seapedia.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White

@Composable
fun PasswordIcon(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    Box {
        Icon(
            modifier = modifier
                .size(Dimens.IconSize)
                .clickable{
                onClick()
            },
            tint = White,
            imageVector = ImageVector.vectorResource(
                if(isVisible) R.drawable.visible else R.drawable.not_visible
            ),
            contentDescription = "Icon Password"
        )
    }
}