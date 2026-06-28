package com.example.seapedia.presentation.seller.product.all.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.ui.theme.Dimens

@Composable
fun ProductListRowSection(
    title: String,
    titleColor: Color? = null,
    products: List<Product>,
    onDelete: (Product) -> Unit,
    onEdit: (Product) -> Unit,
    onDetail : (Product) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        Text(
            text = title,
            color = titleColor ?: MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
            contentPadding = PaddingValues(horizontal = 1.dp)
        ) {
            items(
                items = products,
                key = { it.id }
            ) { product ->

                ProductCard(
                    product = product,
                    onDetail = onDetail,
                    onEdit = onEdit,
                    onDelete = onDelete,
                )
            }

        }

    }
}