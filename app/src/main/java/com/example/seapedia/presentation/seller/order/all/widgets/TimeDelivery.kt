package com.example.seapedia.presentation.seller.order.all.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.global.utils.TimeFormatting
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalTime::class)
@Composable
fun TimeDelivery(
    order: Order,
    progressColor: Color
) {
    val progress =
        ((Clock.System.now() - order.createdAt).inWholeMilliseconds.toFloat() /
                (order.overdue - order.createdAt).inWholeMilliseconds)
            .coerceIn(0f, 1f)
    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = TimeFormatting.formatOrderCard(Clock.System.now()),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = TimeFormatting.formatOrderCard(order.overdue),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium
                )
            )
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = progressColor,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )
    }
}