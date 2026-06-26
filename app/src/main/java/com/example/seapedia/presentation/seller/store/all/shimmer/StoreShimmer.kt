package com.example.seapedia.presentation.seller.store.all.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.seapedia.presentation.common.rememberAppShimmer
import com.example.seapedia.ui.theme.Dimens
import com.valentinilk.shimmer.shimmer

@Composable
fun StoreShimmer(modifier: Modifier = Modifier) {
    val shimmer = rememberAppShimmer()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clip(RoundedCornerShape(16.dp))
            .shimmer(shimmer)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}