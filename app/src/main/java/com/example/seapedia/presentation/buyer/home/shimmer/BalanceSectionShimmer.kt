package com.example.seapedia.presentation.buyer.home.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.seapedia.presentation.common.rememberAppShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun BalanceSectionShimmer(modifier: Modifier = Modifier) {
    val shimmer = rememberAppShimmer()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .shimmer(shimmer)
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(8.dp)
            ).clip(
                shape = RoundedCornerShape(16.dp)
            )
    )
}