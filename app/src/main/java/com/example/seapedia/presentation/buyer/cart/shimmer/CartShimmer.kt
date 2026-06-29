package com.example.seapedia.presentation.buyer.cart.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.seapedia.presentation.common.rememberAppShimmer

import com.valentinilk.shimmer.shimmer


@Composable
fun CartShimmer(modifier: Modifier = Modifier) {
    val shimmer = rememberAppShimmer()
    repeat(4){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(CircleShape)
                .shimmer(shimmer)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }

}