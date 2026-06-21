package com.example.seapedia.presentation.buyer.home.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.seapedia.ui.theme.Dimens

@Composable
fun ReviewTitle(
    modifier: Modifier = Modifier,
    onClickAllReview: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = modifier.padding(vertical = Dimens.SpacePadding),
            text = "Public Reviews",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            modifier = Modifier.clickable(onClick = onClickAllReview),
            text = "See all",
            style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.primary
        ))
    }
}