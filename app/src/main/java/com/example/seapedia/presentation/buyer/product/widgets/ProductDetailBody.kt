package com.example.seapedia.presentation.buyer.product.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.Formatting

@Composable
fun ProductDetailBody(
    modifier: Modifier = Modifier,
    productEntity: ProductEntity
) {
    Text(productEntity.name, style = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Medium
    ))
    Text(text = "Rp ${Formatting.rupiahFormatter.format(productEntity.price)}", style = MaterialTheme.typography.headlineSmall.copy(
        fontSize = 26.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    ))
    AssistChip(
        onClick = {},
        enabled = false,
        label = {
            Text(
                if (productEntity.isAvailable)
                    "Available"
                else
                    "Unavailable"
            )
        }
    )
    if (productEntity.types.isNotEmpty()) {
        Text("Type Product", style = MaterialTheme.typography.bodyMedium)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            productEntity.types.take(3).forEach { type ->
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