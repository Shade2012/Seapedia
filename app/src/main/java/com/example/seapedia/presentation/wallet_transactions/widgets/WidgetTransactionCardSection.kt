package com.example.seapedia.presentation.wallet_transactions.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.WalletTransaction
import com.example.seapedia.ui.theme.Dimens
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun WalletTransactionSection(
    userId: Int,
    transactions: List<WalletTransaction>,
    modifier: Modifier = Modifier,
    onClick: (WalletTransaction) -> Unit = {},
) {
    val grouped = remember(transactions) {
        transactions.groupBy {
            it.createdAt
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .month.name.lowercase()
                .replaceFirstChar(Char::uppercase) +
                    " " +
                    it.createdAt
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .year
        }
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        grouped.forEach { (month, items) ->
            item {
                Text(
                    text = month,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(
                items,
                key = { it.id }
            ) {
                WalletTransactionCard(
                    transaction = it,
                    userId = userId,
                    onClick = { onClick(it) }
                )
            }
        }

        item {
            Spacer(Modifier.height(Dimens.InnerPadding))
        }
    }
}