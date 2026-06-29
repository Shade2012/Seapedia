package com.example.seapedia.presentation.buyer.order.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.seapedia.data.remote.responses.order.DeliveryMethod
import com.example.seapedia.data.remote.responses.order.OrderPreviewResponse
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.ui.theme.Dimens


@Composable
fun OrderPricePreview(
    deliveryMethod: DeliveryMethod,
    orderPreview: OrderPreviewResponse
) {
    val deliveryFeePerKm = when(deliveryMethod) {
        DeliveryMethod.INSTANT -> 3000
        DeliveryMethod.REGULAR -> 1000
        DeliveryMethod.NEXT_DAY -> 1500
    }
    val deliveryBaseFee = when(deliveryMethod) {
        DeliveryMethod.INSTANT -> 10000
        DeliveryMethod.REGULAR -> 5000
        DeliveryMethod.NEXT_DAY -> 7000
    }
    val initialDeliveryPrice = (1000 * orderPreview.distanceJourney) + 5000

    val finalDeliveryPrice = orderPreview.distanceJourney * deliveryFeePerKm + deliveryBaseFee
    val taxFee = orderPreview.taxFee
    val subTotal = orderPreview.subTotal
    val discountPrice = orderPreview.voucherDiscount
    val totalFee = orderPreview.totalFee - initialDeliveryPrice + finalDeliveryPrice
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        PriceSection(title = "Delivery Fee" ,finalDeliveryPrice)
        HorizontalDivider()
        PriceSection(title = "Tax Fee 12%" ,taxFee)
        HorizontalDivider()
        PriceSection(title = "Subtotal" ,subTotal)
        HorizontalDivider()
        if(discountPrice != null){
            PriceSection(title = "Discount Amount" ,discountPrice)
            HorizontalDivider()
        }
        PriceSection(title = "Total Fee" ,totalFee)
    }
}

@Composable
private fun PriceSection(
    title: String,
    price: Int,
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(title, style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold
        ))
        Text(Formatting().formatRupiah(price.toString()), style = MaterialTheme.typography.bodyMedium)
    }
}