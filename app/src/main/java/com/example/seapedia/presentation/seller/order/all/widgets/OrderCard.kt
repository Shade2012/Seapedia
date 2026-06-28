package com.example.seapedia.presentation.seller.order.all.widgets


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.seapedia.R
import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.OrderItem
import com.example.seapedia.global.utils.TimeFormatting
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.ErrorColor
import com.example.seapedia.ui.theme.Green
import com.example.seapedia.ui.theme.Grey
import com.example.seapedia.ui.theme.Yellow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun OrderCard(
    order: Order,
    onClick: () -> Unit,
    onUpdateStatus: (Order) -> Unit = {}
) {
    val textColor = when (order.status) {
        OrderStatus.DONE -> Green
        OrderStatus.PROCCESS -> Yellow
        OrderStatus.RETURN -> Grey
        else -> MaterialTheme.colorScheme.onSurface
    }

    val icon = when(order.status){
        OrderStatus.RETURN -> ImageVector.vectorResource(R.drawable.product_icon)
        OrderStatus.DONE -> Icons.Default.Done
        OrderStatus.All,
        OrderStatus.ON_WAY -> ImageVector.vectorResource(R.drawable.scoopy_icon)
        OrderStatus.WAITING_DRIVER -> ImageVector.vectorResource(R.drawable.waiting_icon)
        OrderStatus.PROCCESS -> ImageVector.vectorResource(R.drawable.order_icon)
    }

    val iconColor = when(order.status){
        OrderStatus.RETURN -> Grey
        OrderStatus.DONE -> Green
        OrderStatus.All,
        OrderStatus.ON_WAY,
        OrderStatus.WAITING_DRIVER -> MaterialTheme.colorScheme.surface
        OrderStatus.PROCCESS -> Yellow
    }

    val color = when(order.status){
        OrderStatus.RETURN -> Grey.copy(alpha = 0.12f)
        OrderStatus.DONE -> Green.copy(alpha = 0.12f)
        OrderStatus.All,
        OrderStatus.ON_WAY,
        OrderStatus.WAITING_DRIVER -> MaterialTheme.colorScheme.surface
        OrderStatus.PROCCESS -> Yellow.copy(alpha = 0.12f)
    }

    val productOverview : (OrderItem) -> String = {
            orderItem ->
        "${orderItem.product.name} (${orderItem.quantity}x)"
    }

    val perProductOverview = order.orderItems.joinToString("; ") {
        productOverview(it)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CardCorner),
        color = color,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 1.dp,
                )
                .padding(Dimens.InnerPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "#${order.id} |", style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.width(Dimens.SpacePadding))
                    IconCustom(
                        contentDescription = "",
                        icon = icon,
                        color = iconColor
                    )
                }
                if(order.status == OrderStatus.PROCCESS)
                    IconButton(
                        onClick = { onUpdateStatus(order) }
                    ) {
                        IconCustom(
                            icon = Icons.Default.Edit,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
            }
            if(order.overdue > Clock.System.now()){
                Text(
                    TimeFormatting.formatOverdueTime(order.overdue), style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = ErrorColor
                    )
                )
                Spacer(modifier = Modifier.width(Dimens.SpacePadding))
            }
            Text(
                text = perProductOverview,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            TimeDelivery(order, textColor)
            FooterOrderCard(
                onClick = onClick
            )
        }
    }
}
