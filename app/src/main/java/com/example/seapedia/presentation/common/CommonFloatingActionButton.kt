package com.example.seapedia.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.seapedia.global.navigation.review.ReviewRoutes
import com.example.seapedia.ui.theme.White

@Composable
fun CommonFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon : ImageVector = Icons.Default.Add,
    contentDescription : String = "Add"
) {
    FloatingActionButton(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = White,
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}