package com.example.seapedia.presentation.buyer.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.utils.Formatting


@Composable
fun ProductPrice(
    product: Product,
    modifier: Modifier = Modifier
) {
    val promo = product.promo?.discount

    val originalPrice = product.price

    val discountedPrice = remember(product) {
        promo?.let {
            originalPrice - (originalPrice * it.discountPercentage / 100)
        } ?: originalPrice
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {

        Text(
            text = Formatting().formatRupiah(discountedPrice.toString()),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )

        if (promo != null && discountedPrice < originalPrice) {

            Spacer(Modifier.width(8.dp))

            Text(
                text = Formatting().formatRupiah(originalPrice.toString()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration = TextDecoration.LineThrough
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "${promo.discountPercentage}% OFF",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}