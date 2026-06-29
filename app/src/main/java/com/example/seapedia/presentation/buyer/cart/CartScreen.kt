package com.example.seapedia.presentation.buyer.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.presentation.buyer.address.all.widgets.addressSection
import com.example.seapedia.presentation.buyer.address.shimmer.BuyerAddressShimmer
import com.example.seapedia.presentation.buyer.cart.shimmer.CartShimmer
import com.example.seapedia.presentation.buyer.cart.widgets.CartItemCard
import com.example.seapedia.presentation.buyer.widgets.BuyerLazyBody
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.ConfirmationDialogCustom
import com.example.seapedia.presentation.common.EmptyCommonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.ErrorColor
import com.example.seapedia.ui.theme.Grey

@Composable
fun CartBuyerScreen(
    buyerNavController: NavController,
    viewModel: CartViewModel = hiltViewModel<CartViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val clearAction = "Clear Cart"
    val checkoutAction = "Checkout Cart"

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onInit()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    var confirmAction by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    BuyerLazyBody(
        onRefresh = viewModel::onRefresh,
        isRefreshing = state.isRefreshing,
    ) {
        item {
            TopAppBarCustom(
                title = "Cart"
            )
        }
        when (val result = state.cart) {
            is CommonState.Error<*> -> {
                item {
                    FailedCommonCustom(text = result.message)
                }
            }

            is CommonState.Loading -> {
                item {
                    CartShimmer()
                }
            }

            is CommonState.Success -> {
                if (result.data.cartItems.isEmpty()) {
                    item {
                        EmptyCommonCustom(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Your cart is empty"
                        )
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(
                                    width = 2.dp,
                                    color = Grey,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(Dimens.InnerPadding),
                            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
                        ) {
                            result.data.cartItems.forEachIndexed { index, cartItem ->
                                key(cartItem.id) {
                                    CartItemCard(
                                        cartItem = cartItem,
                                        onIncrease = { _, _ ->
                                            viewModel.onIncrement(cartItem.quantity,cartItem.id,cartItem.product.stock)
                                        },
                                        onDecrease = {
                                                _, _ ->
                                            viewModel.onDecrement(cartItem.quantity,cartItem.id)
                                        },
                                        onClick = {
                                            buyerNavController.navigate(BuyerRoute.ProductDetail.createRoute(it))
                                        }
                                    )

                                    if (index != result.data.cartItems.lastIndex) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(vertical = Dimens.SpacePadding)
                                        )
                                    }
                                }
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = Dimens.SpacePadding)
                            )
                            TotalSection(result.data.subTotal)
                            ButtonCustom(
                                enabled = true,
                                isNotLoading = true,
                                containerColor = ErrorColor,
                                title = "Clear Cart",
                                onClick = {
                                    confirmAction = clearAction
                                }
                            )
                            ButtonCustom(
                                enabled = true,
                                isNotLoading = true,
                                title = "Checkout",
                                onClick = {
                                    confirmAction = checkoutAction
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    ConfirmationDialogCustom(
        visible = confirmAction != null,
        title = confirmAction ?: "",
        message = "Are you sure want to do this action? this action cannot be undone.",
        confirmText = confirmAction ?: "",
        onDismiss = {
            confirmAction = null
        },
        onConfirm = {
            confirmAction?.let {
                if(confirmAction == checkoutAction){
                    Unit
                }
                if (confirmAction == clearAction){
                    viewModel.onClearCart()
                }
            }
            confirmAction = null
        }
    )
}

@Composable
fun TotalSection(subTotal: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Subtotal",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = Formatting().formatRupiah(subTotal.toString()),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}