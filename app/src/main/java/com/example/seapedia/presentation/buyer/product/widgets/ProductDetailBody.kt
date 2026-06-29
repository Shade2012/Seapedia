package com.example.seapedia.presentation.buyer.product.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.presentation.buyer.widgets.ProductPrice

@Composable
fun ProductDetailBody(
    modifier: Modifier = Modifier,
    product: Product
) {
    Text(product.name, style = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Medium
    ))
    ProductPrice(product)
    AssistChip(
        onClick = {},
        enabled = false,
        label = {
            Text(
                if (product.isAvailable)
                    "Available"
                else
                    "Unavailable"
            )
        }
    )
    if (product.types.isNotEmpty()) {
        Text("Type Product", style = MaterialTheme.typography.bodyMedium)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            product.types.take(3).forEach { type ->
                SuggestionChip(
                    onClick = {},
                    label = {
                        Text(type.name)
                    }
                )
            }
        }
    }

//    Text(productEntity.stock)
//    Text(productEntity.category)
//    Text(productEntity.types)
}