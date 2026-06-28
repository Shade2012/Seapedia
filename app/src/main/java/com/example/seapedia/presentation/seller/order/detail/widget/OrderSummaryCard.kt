package com.example.seapedia.presentation.seller.order.detail.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.ui.theme.Dimens

@Composable
fun OrderSummaryCard(
    order: Order
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        OrderSummaryField(
            title = "Sub Total",
            amount = Formatting().formatRupiah(order.subTotal.toString()),
        )
        OrderSummaryField(
            title = "Delivery Fee ${order.deliveryMethod.displayName}",
            amount = Formatting().formatRupiah(order.deliveryFee.toString()),
        )
        OrderSummaryField(
            title = "Tax Fee 12%",
            amount = Formatting().formatRupiah(order.taxFee.toString()),
        )
        OrderSummaryField(
            title = "Total Fee",
            amount = Formatting().formatRupiah(order.totalFee.toString()),
        )
    }
}

@Composable
fun OrderSummaryField(
    title:String,
    amount: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(amount,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}