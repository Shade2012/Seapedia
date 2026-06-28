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
import com.example.seapedia.domain.entities.OrderItem
import com.example.seapedia.domain.entities.OrderItemType
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.presentation.common.ImageCustom
import com.example.seapedia.ui.theme.Dimens
import kotlin.collections.forEach


@Composable
fun OrderItemCard(
    orderItem: OrderItem
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        ImageCustom(
            contentDescription = "Image Product",
            imageUrl = orderItem.product.listImages[0].imageUrl
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {
            Text(orderItem.product.name, style = MaterialTheme.typography.bodyMedium)
            OrderItemTypeSection(orderItem.orderItemType)
        }
        Row {
            Text(
                text = Formatting().formatRupiah(orderItem.subTotal.toString()),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = " = ${Formatting().formatRupiah((orderItem.product.price - orderItem.promoDiscount).toString())} x ${orderItem.quantity}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

        }
    }
}

@Composable
fun OrderItemTypeSection(
    types: List<OrderItemType>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        types.forEach { type ->
            Text(
                text = buildString {
                    append(type.productType.name)
                    append(" : ")

                    append(
                        type.productTypeItem.joinToString(", ") {
                            it.productTypeItemOrderItem.name
                        }
                    )
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}