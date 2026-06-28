package com.example.seapedia.presentation.wallet_transactions.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.WalletTransaction
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.global.utils.TimeFormatting.toDisplayString
import com.example.seapedia.ui.theme.ErrorColor
import com.example.seapedia.ui.theme.Green
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun WalletTransactionCard(
    transaction: WalletTransaction,
    userId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    val isIncome =
        transaction.receiver == userId &&
                transaction.sender != userId

    val background =
        if (isIncome)
            Green.copy(alpha = 0.12f)
        else
            ErrorColor.copy(alpha = 0.12f)

    val amountColor =
        if (isIncome) Green else ErrorColor

    val amountPrefix =
        if (isIncome) "+" else "-"

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(
            containerColor = background
        ),
        onClick = onClick
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    transaction.type.displayName,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "$amountPrefix${Formatting().formatRupiah(transaction.amount.toString())}",
                    color = amountColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = transaction.description,
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider()

            Text(
                text = transaction.createdAt.toDisplayString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}