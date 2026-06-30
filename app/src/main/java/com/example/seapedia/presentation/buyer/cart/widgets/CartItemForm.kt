package com.example.seapedia.presentation.buyer.cart.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.seapedia.R
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.ProductType
import com.example.seapedia.domain.entities.ProductTypeItem
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.presentation.buyer.cart.state.CartItemFormState
import com.example.seapedia.presentation.buyer.cart.state.CartProductTypeForm
import com.example.seapedia.ui.theme.Dimens

@Composable
fun CartItemForm(
    modifier: Modifier = Modifier,
    product: Product,
    state: CartItemFormState,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onSingleTypeSelected: (productTypeId: Int, itemId: Int) -> Unit,
    onMultipleTypeToggled: (productTypeId: Int, itemId: Int) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {

        QuantitySelector(
            quantity = state.quantity,
            onIncrease = onIncreaseQuantity,
            onDecrease = onDecreaseQuantity
        )

        product.types.forEach { type ->

            HorizontalDivider()

            ProductTypeSection(
                productType = type,
                selected = state.selectedTypes.firstOrNull {
                    it.productTypeId == type.id
                },
                onSingleSelected = { itemId ->
                    onSingleTypeSelected(type.id, itemId)
                },
                onMultipleSelected = { itemId ->
                    onMultipleTypeToggled(type.id, itemId)
                }
            )
        }

        HorizontalDivider()

        CartSubtotalSection(
            product = product,
            quantity = state.quantity,
            selectedTypes = state.selectedTypes
        )
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "Quantity",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onDecrease
            ) {

                Icon(
                    ImageVector.vectorResource(R.drawable.minus_icon),
                    null
                )
            }

            Text(
                quantity.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = onIncrease
            ) {

                Icon(
                    Icons.Default.Add,
                    null
                )
            }
        }
    }
}

@Composable
fun ProductTypeSection(
    productType: ProductType,
    selected: CartProductTypeForm?,
    onSingleSelected: (Int) -> Unit,
    onMultipleSelected: (Int) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            buildString {

                append(productType.name)

                if (productType.isRequired)
                    append(" *")

                if (productType.isMultiple)
                    append(" (Multiple)")
            },
            style = MaterialTheme.typography.titleSmall
        )

        productType.listItems.orEmpty().filter {
            it.stock > 0
        }.forEach { item ->

            if (productType.isMultiple) {

                ProductTypeMultipleChoice(
                    item = item,
                    checked = selected
                        ?.selectedItemIds
                        ?.contains(item.id)
                        ?: false,
                    onChecked = {
                        onMultipleSelected(item.id)
                    }
                )

            } else {

                ProductTypeSingleChoice(
                    item = item,
                    selected = selected
                        ?.selectedItemIds
                        ?.firstOrNull() == item.id,
                    onSelected = {
                        onSingleSelected(item.id)
                    }
                )

            }
        }
    }
}

@Composable
fun ProductTypeSingleChoice(
    item: ProductTypeItem,
    selected: Boolean,
    onSelected: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected,
            onClick = onSelected
        )

        Text(
            modifier = Modifier.weight(1f),
            text = item.name
        )

        if (item.price > 0) {

            Text(
                "+${Formatting().formatRupiah(item.price.toString())}"
            )
        }
    }
}

@Composable
fun ProductTypeMultipleChoice(
    item: ProductTypeItem,
    checked: Boolean,
    onChecked: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onChecked),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = {
                onChecked()
            }
        )

        Text(
            modifier = Modifier.weight(1f),
            text = item.name
        )

        if (item.price > 0) {

            Text(
                "+${Formatting().formatRupiah(item.price.toString())}"
            )
        }
    }
}

@Composable
fun CartSubtotalSection(
    product: Product,
    quantity: Int,
    selectedTypes: List<CartProductTypeForm>
) {

    val additionalPrice = remember(selectedTypes) {

        product.types.sumOf { type ->

            val selected = selectedTypes.firstOrNull {
                it.productTypeId == type.id
            }

            type.listItems
                .orEmpty()
                .filter {
                    selected?.selectedItemIds?.contains(it.id) == true
                }
                .sumOf {
                    it.price
                }
        }
    }

    val discount = if (product.promo != null) {
        product.price * product.promo.discount.discountPercentage / 100
    } else {
        0
    }

    val subtotal = (product.price + additionalPrice - discount) * quantity

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            "Subtotal",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            Formatting().formatRupiah(subtotal.toString()),
            style = MaterialTheme.typography.titleLarge
        )
    }
}