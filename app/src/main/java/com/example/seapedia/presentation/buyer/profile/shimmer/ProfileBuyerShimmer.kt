package com.example.seapedia.presentation.buyer.profile.shimmer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
fun ProfileBuyerShimmer(
    modifier: Modifier = Modifier
) {
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

        // Profile image
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .shimmer(shimmer)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        // Profile card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.InnerPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
            ) {

                // Full name
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(28.dp)
                        .shimmer(shimmer)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(8.dp)
                        )
                )

                // Email
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(20.dp)
                        .shimmer(shimmer)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(8.dp)
                        )
                )

                HorizontalDivider()

                // Roles title
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .height(18.dp)
                        .shimmer(shimmer)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(8.dp)
                        )
                )

                // Roles value
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(20.dp)
                        .shimmer(shimmer)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(8.dp)
                        )
                )
            }
        }

        // Logout button placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmer(shimmer)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}