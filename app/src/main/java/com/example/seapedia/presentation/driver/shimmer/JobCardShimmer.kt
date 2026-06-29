package com.example.seapedia.presentation.driver.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.seapedia.ui.theme.Dimens
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer


@Composable
fun JobCardShimmer(
    shimmer: Shimmer
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CardCorner)
    ) {
        Column(
            modifier = Modifier.padding(Dimens.InnerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Earning
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmer(shimmer)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            // Expired
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmer(shimmer)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(Modifier.height(4.dp))

            // Store address
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmer(shimmer)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            // Customer address
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmer(shimmer)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            // Distance
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmer(shimmer)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            HorizontalDivider()

            // Products
            repeat(3) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .height(16.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .shimmer(shimmer)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )

                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(16.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .shimmer(shimmer)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            }
        }
    }
}