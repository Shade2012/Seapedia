package com.example.seapedia.presentation.seller.home.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.presentation.common.EmptyCommonCustom
import com.example.seapedia.presentation.seller.order.all.widgets.OrderCard
import com.example.seapedia.ui.theme.Dimens

@Composable
fun HomeOrderListSection(
    orders: List<Order>,
    onClick: (Order) -> Unit,
    onUpdateStatus: (Order) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Waiting to be proccess",
            style = MaterialTheme.typography.titleMedium
        )
    }
    if(orders.isEmpty()){
        EmptyCommonCustom(
            text = "No orders waiting to be processed"
        )
    }else {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
            contentPadding = PaddingValues(horizontal = Dimens.SpacePadding)
        ) {
            items(
                orders,
                key = {order -> order.id}
            ){
                    order ->
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(260.dp)
                ) {
                    OrderCard(
                        order,
                        onClick = {
                            onClick(order)
                        },
                        onUpdateStatus = onUpdateStatus
                    )
                }
            }
        }
    }
}