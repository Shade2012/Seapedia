package com.example.seapedia.presentation.driver.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.Job
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.global.utils.TimeFormatting.toDisplayString
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.ErrorColor
import com.example.seapedia.ui.theme.Green
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun JobCard(
    job: Job,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val formatter = Formatting()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CardCorner),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(Dimens.InnerPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = "Earning",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = formatter.formatRupiah(job.earning.toString()),
                        style = MaterialTheme.typography.titleSmall,
                        color = Green,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Expires",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = job.expiredDate.toDisplayString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = ErrorColor
                    )
                }
            }

            HorizontalDivider()

            InfoRow(
                title = "Distance",
                value = "${job.order.distanceJourneyKm} km"
            )

            InfoColumn(
                title = "Pickup",
                value = job.order.store.address
            )

            InfoColumn(
                title = "Destination",
                value = job.order.orderAddress.receiverAddress
            )

            HorizontalDivider()

            Text(
                text = "Products",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            job.order.orderItems.forEach { item ->
                Text(
                    text = "• ${item.quantity}x ${item.product.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(0.35f)
        )

        Text(
            text = value,
            modifier = Modifier.weight(0.65f),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun InfoColumn(
    title: String,
    value: String
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}