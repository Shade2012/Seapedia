package com.example.seapedia.presentation.seller.product.all.shimmer

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
fun ProductSellerScreenShimmer(modifier: Modifier = Modifier) {
    val shimmer = rememberAppShimmer()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.InnerPadding)
            .padding(top = Dimens.TopPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmer(shimmer)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {
            repeat(2){
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmer(shimmer)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmer(shimmer)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmer(shimmer)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}