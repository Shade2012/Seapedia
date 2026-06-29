package com.example.seapedia.presentation.driver.shimmer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.seapedia.presentation.common.rememberAppShimmer
import com.example.seapedia.ui.theme.Dimens

@Composable
fun JobListRowSectionShimmer(
    modifier: Modifier = Modifier
) {
    val shimmer = rememberAppShimmer()

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
        contentPadding = PaddingValues(horizontal = Dimens.InnerPadding)
    ) {
        items(4) {
            Box(
                modifier = Modifier.width(320.dp)
            ) {
                JobCardShimmer(shimmer)
            }
        }
    }
}