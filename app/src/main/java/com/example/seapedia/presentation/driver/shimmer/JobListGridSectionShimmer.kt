package com.example.seapedia.presentation.driver.shimmer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seapedia.presentation.common.rememberAppShimmer
import com.example.seapedia.ui.theme.Dimens
import com.valentinilk.shimmer.shimmer

@Composable
fun JobListGridSectionShimmer(
    modifier: Modifier = Modifier
) {
    val shimmer = rememberAppShimmer()

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        items(4) {
            JobCardShimmer(shimmer)
        }
    }
}