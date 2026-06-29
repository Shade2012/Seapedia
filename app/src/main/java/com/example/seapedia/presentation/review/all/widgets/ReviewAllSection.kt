package com.example.seapedia.presentation.review.all.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.presentation.review.widgets.ReviewCard
import com.example.seapedia.ui.theme.Dimens
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun ReviewAllSection(
    modifier: Modifier = Modifier,
    reviews: List<Review>,
    daySystem: Instant
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        items(
            items = reviews,
            key = { it.id }
        ) { review ->
            ReviewCard(
                review = review,
                daySystem = daySystem
            )
        }
    }
}