package com.example.seapedia.presentation.buyer.cart.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.seapedia.data.remote.responses.carts.CartItemResponse
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.presentation.buyer.cart.widgets.CartItemCard
import com.example.seapedia.presentation.buyer.widgets.BottomSheetCard
import com.example.seapedia.presentation.buyer.widgets.BottomSheetShimmer
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.ui.theme.Dimens


@Composable
fun CartItemBottomSheet(
    isVisible: Boolean,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    cartItemList: List<CartItemResponse>,
    product: Product,
    onDecrement: (cartItemId: Int, quantity:Int) -> Unit,
    onIncrement: (cartItemId: Int, quantity:Int) -> Unit,
    onUpdateProduct: () -> Unit,
    onAddCartItem: () -> Unit,
) {
    BottomSheetCard(
        visible = isVisible,
        onDismiss = onDismiss
    ) {
        if(!isLoading){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f).padding(
                        horizontal = Dimens.InnerPadding,
                        vertical = Dimens.SpacePadding
                    )
            )
            {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(Dimens.SpacePadding))

                Box(
                    modifier = Modifier
                        .weight(0.9f)
                        .fillMaxWidth()
                ) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            bottom = 140.dp // height of the buttons
                        ),
                        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
                    ) {
                        items(
                            items = cartItemList,
                            key = { it.id }
                        ) {
                            CartItemCard(
                                cartItem = it,
                                onClick = {},
                                onDecrease = onDecrement,
                                onIncrease = onIncrement
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(top = 8.dp)
                    ) {

                        ButtonCustom(
                            modifier = Modifier.fillMaxWidth(),
                            title = "Add Cart Item",
                            enabled = true,
                            isNotLoading = true,
                            onClick = onAddCartItem
                        )
                    }
                }
            }
        }else{
            BottomSheetShimmer()
        }

    }
}
