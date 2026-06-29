package com.example.seapedia.presentation.driver.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.Job
import com.example.seapedia.presentation.common.EmptyCommonCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun JobListRowSection(
    title: String,
    jobs: List<Job>,
    modifier: Modifier = Modifier,
    onClick: (Job) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        if (jobs.isEmpty()) {
            EmptyCommonCustom(text = "No jobs available")
            return
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
            contentPadding = PaddingValues(horizontal = Dimens.InnerPadding)
        ) {
            this.items(
                items = jobs,
                key = { it.id }
            ) { job ->
                Box(
                    modifier = Modifier.width(320.dp)
                ) {
                    JobCard(
                        job = job,
                        onClick = { onClick(job) }
                    )
                }
            }
        }
    }
}