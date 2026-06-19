package com.example.seapedia.presentation.common

import androidx.compose.foundation.background
import com.example.seapedia.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.seapedia.ui.theme.White

@Composable
fun IconApp(modifier: Modifier = Modifier) {
    Box (
        modifier
            .size(100.dp)
            .shadow(
                elevation = 8.dp,
                shape = CircleShape
            )
            .clip(RoundedCornerShape(16.dp))
            .background(White),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ship),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Ship",
            modifier = Modifier.size(48.dp)
        )
    }
}