package com.example.seapedia.presentation.buyer.home.widgets

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.presentation.review.widgets.ReviewCard
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
fun LazyGridScope.reviewSection(
    modifier: Modifier = Modifier,
    reviews: List<Review>,
    onClickAllReview: () -> Unit,
    daySystem: Instant
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        ReviewTitle(onClickAllReview = onClickAllReview)
    }
    items(
        items = reviews,
        key = { it.id },
        span = { GridItemSpan(maxLineSpan) }
    ) { review ->
        ReviewCard(
            review = review,
            daySystem = daySystem
        )
    }
}