package com.example.seapedia.presentation.seller.product.all.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.ui.theme.Dimens

fun LazyListScope.productGridSection(
    products: List<ProductEntity>,
    onDelete: (ProductEntity) -> Unit,
    onDetail: (ProductEntity) -> Unit,
    onEdit: (ProductEntity) -> Unit
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