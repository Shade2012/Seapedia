package com.example.seapedia.presentation.seller.product.all.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.ui.theme.Dimens

fun LazyListScope.productGridSection(
    products: List<Product>,
    onDelete: (Product) -> Unit,
    onDetail: (Product) -> Unit,
    onEdit: (Product) -> Unit
) {
    items(products.chunked(2)) { rowProducts ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {

            rowProducts.forEach { product ->

                ProductCard(
                    modifier = Modifier.weight(1f),
                    product = product,
                    onDelete = onDelete,
                    onEdit = onEdit,
                    onDetail = onDetail
                )

            }

            if (rowProducts.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}