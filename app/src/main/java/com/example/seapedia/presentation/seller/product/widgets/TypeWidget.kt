package com.example.seapedia.presentation.seller.product.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.seapedia.data.remote.body.CreateProductType
import com.example.seapedia.data.remote.body.CreateProductTypeItem
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.global.utils.NormalSupportingText
import com.example.seapedia.global.utils.NumberSupportingText
import com.example.seapedia.global.utils.RupiahSupportingText
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.ErrorColor
import kotlin.text.isDigit

@Composable
fun TypeWidget(
    index: Int,
    type: CreateProductType,
    enable: Boolean = true,

    onDeleteType: (Int) -> Unit,

    onNameChange: (Int, String) -> Unit,

    onRequiredChange: (Int, Boolean) -> Unit,

    onMultipleChange: (Int, Boolean) -> Unit,

    onAddItem: (Int) -> Unit,

    onDeleteItem: (Int, Int) -> Unit,

    onItemNameChange: (Int, Int, String) -> Unit,

    onItemPriceChange: (Int, Int, String) -> Unit,

    onItemStockChange: (Int, Int, String) -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(enable)
    }
    val rotation by animateFloatAsState(
        if (expanded) 90f else 0f,
        label = "Arror Rotation"
    )
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(Dimens.InnerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    modifier = Modifier.rotate(rotation),
                    contentDescription = null
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = type.name.ifBlank { "Variant ${index + 1}" },
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                if(enable)
                    IconButton(
                    onClick = {
                        onDeleteType(index)
                    }
                ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null
                        )
                    }

            }

            AnimatedVisibility(
                visible = expanded
            )
            {

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextFieldCustom(
                        title = "Type Name",
                        hint = "Example : Color",
                        text = type.name,
                        imeAction = ImeAction.Done,
                        supportingText = NormalSupportingText,
                        keyboardType = KeyboardType.Text,
                        enabled = enable,
                    ) {
                        onNameChange(index, it)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Required",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = type.isRequired,
                            onCheckedChange = {
                                if(enable)
                                    onRequiredChange(index, it)
                            }
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Multiple Choice",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = type.isMultiple,
                            onCheckedChange = {
                                if(enable)
                                    onMultipleChange(index, it)
                            }
                        )
                    }
                    HorizontalDivider()
                    Text(
                        text = "Items",
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (type.items.isEmpty()) {
                        Text(
                            text = "No items yet.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "At least have one item",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ErrorColor
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    } else {
                        type.items.forEachIndexed { itemIndex, item ->
                            TypeItemWidget(
                                itemIndex = itemIndex,
                                item = item,
                                enable = enable,

                                onDelete = {
                                    onDeleteItem(index, itemIndex)
                                },

                                onNameChange = {
                                    onItemNameChange(index, itemIndex, it)
                                },

                                onPriceChange = {
                                    onItemPriceChange(index, itemIndex, it)
                                },

                                onStockChange = {
                                    onItemStockChange(index, itemIndex, it)
                                }
                            )

                        }
                    }

                    if(enable){
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onAddItem(index)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Add Item", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun TypeItemWidget(
    itemIndex: Int,
    item: CreateProductTypeItem,
    enable: Boolean,

    onDelete: () -> Unit,

    onNameChange: (String) -> Unit,

    onPriceChange: (String) -> Unit,

    onStockChange: (String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(Dimens.InnerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Item ${itemIndex + 1}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                if(enable)
                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Item"
                        )
                    }
            }

            TextFieldCustom(
                title = "Name",
                hint = "(e.g. Black)",
                text = item.name,
                imeAction = ImeAction.Next,
                enabled = enable,
                supportingText = NormalSupportingText,
                keyboardType = KeyboardType.Text
            ) {
                onNameChange(it)
            }

            TextFieldCustom(
                title = "Additional Price",
                hint = "0",
                enabled = enable,
                keyboardType = KeyboardType.Number,
                text = Formatting().formatRupiah(item.price.toString()),
                supportingText = RupiahSupportingText,
                imeAction = ImeAction.Next
            ) {
                value ->
                onPriceChange(value.filter {it.isDigit() })
            }

            TextFieldCustom(
                title = "Stock",
                hint = "0",
                enabled = enable,
                keyboardType = KeyboardType.Number,
                supportingText = NumberSupportingText,
                text = item.stock.toString(),
                imeAction = ImeAction.Done
            ) {
                value ->
                onStockChange(value.filter {it.isDigit()})
            }

        }

    }

}