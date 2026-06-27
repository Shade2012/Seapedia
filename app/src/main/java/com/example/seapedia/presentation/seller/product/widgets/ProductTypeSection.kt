package com.example.seapedia.presentation.seller.product.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.seapedia.data.remote.body.CreateProductType

@Composable
fun ProductTypeSection(
    enable: Boolean = true,
    types: List<CreateProductType>,
    onDeleteType: (Int) -> Unit,
    onChangeTypeName: (Int, String) -> Unit,
    onChangeTypeRequired: (Int, Boolean) -> Unit,
    onChangeTypeMultiple: (Int, Boolean) -> Unit,
    onAddItem: (Int) -> Unit,
    onDeleteItem: (Int, Int) -> Unit,
    onChangeItemName: (Int, Int, String) -> Unit,
    onChangeItemPrice: (Int, Int, String) -> Unit,
    onChangeItemStock: (Int, Int, String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Types",
            style = MaterialTheme.typography.titleLarge
        )
        if (types.isEmpty()) {
            Text(
                text = "No Types yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            types.forEachIndexed { index, type ->
                TypeWidget(
                    index = index,
                    type = type,
                    enable = enable,
                    onDeleteType = onDeleteType,
                    onNameChange = onChangeTypeName,
                    onRequiredChange = onChangeTypeRequired,
                    onMultipleChange = onChangeTypeMultiple,
                    onAddItem = onAddItem,
                    onDeleteItem = onDeleteItem,
                    onItemNameChange = onChangeItemName,
                    onItemPriceChange = onChangeItemPrice,
                    onItemStockChange = onChangeItemStock
                )
            }
        }
    }
}
