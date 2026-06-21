package com.example.seapedia.presentation.review.all.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seapedia.domain.entities.ReviewEntity
import com.example.seapedia.presentation.review.widgets.ReviewCard
import com.example.seapedia.ui.theme.Dimens

@Composable
fun ReviewAllSection(
    modifier: Modifier = Modifier,
    reviews: List<ReviewEntity>
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
                review = review
            )
        }
    }
}