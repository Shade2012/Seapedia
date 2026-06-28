package com.example.seapedia.presentation.seller.order.all.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.global.navigation.seller.SellerRoute


fun LazyGridScope.orderListSection(
    title: String,
    orders: List<Order>,
    onUpdate:(Order) -> Unit,
    sellerNavController: NavController
) {
    if (orders.isEmpty()) return

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text("${orders.size} Orders", style = MaterialTheme.typography.bodyMedium)
        }
    }
    items(
        items = orders,
        key = { it.id }
    ) { order ->
        OrderCard(
            order = order,
            onClick = {
                sellerNavController.navigate(SellerRoute.OrderDetail.createRoute(order.id))
            },
            onUpdateStatus = onUpdate
        )
    }
}