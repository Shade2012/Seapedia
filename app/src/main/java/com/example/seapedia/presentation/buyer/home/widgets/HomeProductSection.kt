package com.example.seapedia.presentation.buyer.home.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.presentation.common.EmptyCommonCustom
import com.example.seapedia.ui.theme.Dimens

fun LazyGridScope.searchProductSection(
    products: List<Product>,
    searchName: String,
    buyerNavController: NavController,
    isGuest: Boolean
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = "Search Result for : $searchName",
            style = MaterialTheme.typography.headlineSmall
        )
    }

    if (products.isEmpty()) {

        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            EmptyCommonCustom(
                text = "Empty Products"
            )
        }

    } else {

        items(
            items = products,
            key = { it.id }
        ) { product ->
            HomeProductCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                product = product,
                isGuest = isGuest,
                onClick = {
                    buyerNavController.navigate(BuyerRoute.ProductDetail.createRoute(product.id))
                }
            )
        }
    }
}

fun LazyGridScope.defaultProductSection(
    products: List<Product>,
    buyerNavController: NavController,
    isGuest: Boolean
) {

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {

        if (products.isEmpty()) {

            EmptyCommonCustom(
                text = "Products Not Found"
            )

        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(
                    Dimens.RowSpacePadding
                )
            ) {

                items(
                    items = products,
                    key = { it.id }
                ) { product ->

                    HomeProductCard(
                        modifier = Modifier,
                        product = product,
                        isGuest = isGuest,
                        onClick = {
                            buyerNavController.navigate(BuyerRoute.ProductDetail.createRoute(product.id))
                        }
                    )
                }
            }
        }
    }
}