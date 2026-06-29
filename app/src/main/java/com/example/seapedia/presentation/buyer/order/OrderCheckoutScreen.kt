package com.example.seapedia.presentation.buyer.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.ALL_DELIVERY_METHOD
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.auth.login.widgets.DropdownCustom
import com.example.seapedia.presentation.buyer.address.all.widgets.AddressCard
import com.example.seapedia.presentation.buyer.cart.shimmer.CartShimmer
import com.example.seapedia.presentation.buyer.cart.widgets.CartItemCard
import com.example.seapedia.presentation.buyer.order.widgets.AddressSelection
import com.example.seapedia.presentation.buyer.order.widgets.DeliveryMethodInfo
import com.example.seapedia.presentation.buyer.order.widgets.OrderPricePreview
import com.example.seapedia.presentation.buyer.widgets.BuyerBody
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun OrderCheckoutScreen(
    buyerNavController : NavController,
    viewModel: OrderCheckoutViewModel = hiltViewModel<OrderCheckoutViewModel>(),
) {
    var isVisible by remember { mutableStateOf(false) }
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    val refresh = buyerNavController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_address",false)
    LaunchedEffect(Unit) {
        viewModel.navigateToCart.collect {
            buyerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_cart",true)
            buyerNavController.popBackStack()
        }
    }
    LaunchedEffect(refresh) {
        refresh?.collect { shouldRefresh ->
            if (shouldRefresh) {
                viewModel.onInit(false)
                buyerNavController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("refresh_address", false)
            }
        }
    }
    BuyerBody(
        scrollState = scrollState,
        enableRefresh = true,
        onRefresh = viewModel::onRefresh,
        isRefreshing = state.isRefreshing
    ) {
        TopAppBarCustom(onBackClick = {
            buyerNavController.popBackStack()
        },title = "Checkout Order")
        when(val resultPreview = state.orderPreviewResponse){
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = resultPreview.message)
            }
            is CommonState.Loading<*> -> {
                CircularProgressIndicator()
            }
            is CommonState.Success -> {
                when(val result = state.cart){
                    is CommonState.Error<*> -> {
                        FailedCommonCustom(text = result.message)
                    }
                    is CommonState.Loading<*> -> {
                        CartShimmer()
                    }
                    is CommonState.Success-> {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
                        ) {
                            items(
                                items = result.data.cartItems,
                                key = {it.id}
                            ){ cartItem ->
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
                            }
                            item {
                                TextFieldCustom(
                                    text = state.voucherCode ?: "",
                                    hint = "Voucher Code",
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Text,
                                    onTextChange = viewModel::onChangeVoucherCode
                                )
                            }
                            item {
                                DropdownCustom(
                                    modifier = Modifier,
                                    list = ALL_DELIVERY_METHOD,
                                    selectedItem = state.selectedDeliveryMethod,
                                    onItemSelected = viewModel::onChangeDeliveryMethod,
                                    itemLabel = { it.displayName },
                                    label = "Delivery Method"
                                )
                            }
                            item {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ),
                                    elevation = CardDefaults.elevatedCardElevation(
                                        defaultElevation = 2.dp
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(Dimens.InnerPadding),
                                        verticalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {

                                        Text(
                                            text = "Delivery Information",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )

                                        DeliveryMethodInfo(
                                            name = "Instant",
                                            price = "Rp 10.000",
                                            perKm = "Rp 3.000/km",
                                            estimate = "≈ 3 Hours"
                                        )

                                        HorizontalDivider()

                                        DeliveryMethodInfo(
                                            name = "Regular",
                                            price = "Rp 5.000",
                                            perKm = "Rp 1.000/km",
                                            estimate = "2–3 Days"
                                        )

                                        HorizontalDivider()

                                        DeliveryMethodInfo(
                                            name = "Next Day",
                                            price = "Rp 7.000",
                                            perKm = "Rp 1.500/km",
                                            estimate = "Next Day"
                                        )
                                    }
                                }
                            }
                            item {
                                OrderPricePreview(
                                    deliveryMethod = state.selectedDeliveryMethod,
                                    orderPreview = resultPreview.data
                                )
                            }
                        }
                        if(state.selectedAddress!= null)
                            AddressCard(
                                modifier = Modifier.clickable{
                                    isVisible = true
                                },
                                enableDelete = false,
                                address = state.selectedAddress,
                                onClickUpdate = { buyerNavController.navigate(BuyerRoute.BuyerUpdateAddress.createRoute(addressId = it.id)) },
                                onClickDelete = {},
                            )
                        ButtonCustom(
                            title = "Checkout Now",
                            enabled = !state.isConfirmLoading && !state.isLoading,
                            isNotLoading = !state.isLoading,
                            onClick = viewModel::checkout
                        )
                    }
                }
            }
        }
    }
    when(val addresses = state.listAddress){
        is CommonState.Error<*> -> {}
        is CommonState.Loading<*> -> {}
        is CommonState.Success -> {
            AddressSelection(
                isVisible = isVisible,
                isLoading = state.isLoading,
                onDismiss = {
                    isVisible = false
                },
                addresses =addresses.data,
                buyerNavController = buyerNavController,
                onClick = viewModel::onChangeAddress,
            )
        }
    }
}

