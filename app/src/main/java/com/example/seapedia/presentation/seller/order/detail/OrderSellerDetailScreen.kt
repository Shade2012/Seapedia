package com.example.seapedia.presentation.seller.order.detail


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.seller.order.detail.shimmer.OrderSellerDetailShimmer
import com.example.seapedia.presentation.seller.order.detail.widget.OrderAddressCard
import com.example.seapedia.presentation.seller.order.detail.widget.OrderDetailCard
import com.example.seapedia.presentation.seller.order.detail.widget.OrderItemCard
import com.example.seapedia.presentation.seller.order.detail.widget.OrderSummaryCard
import com.example.seapedia.presentation.seller.order.detail.widget.OrderTimelineCard
import com.example.seapedia.presentation.seller.order.widgets.OrderStatusBottomSheet
import com.example.seapedia.presentation.seller.widgets.SellerBody
import com.example.seapedia.ui.theme.Dimens

@Composable
fun OrderSellerDetailScreen(
    sellerNavController: NavController,
    viewModel: OrderSellerDetailViewModel = hiltViewModel<OrderSellerDetailViewModel>()
) {
    val role = viewModel.sessionState.value.role ?: UserRole.Guest
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var orderToEdit by rememberSaveable {
        mutableStateOf<Order?>(null)
    }
    LaunchedEffect(Unit) {
        viewModel.refreshOrderAll.collect {
            sellerNavController.previousBackStackEntry?.savedStateHandle?.set("refresh_order",true)
        }
    }
    SellerBody(
        scrollState,
        enableRefresh = true,
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::refreshDetail
    ){
        TopAppBarCustom(
            onBackClick = {
                sellerNavController.popBackStack()
            },
            title = "Detail Order"
        )
        when{
            state.isLoading -> {
                OrderSellerDetailShimmer()
            }

            state.order is CommonState.Error -> {
                FailedCommonCustom(text = state.order.message)
            }

            state.orderHistories is CommonState.Error -> {
                FailedCommonCustom(text = state.orderHistories.message)
            }

            state.order is CommonState.Success &&
                    state.orderHistories is CommonState.Success -> {
                val order = state.order.data
                val histories = state.orderHistories.data
                if(order.status == OrderStatus.PROCCESS && role == UserRole.Seller)
                    ButtonCustom(
                        enabled = !state.isLoading,
                        title = "Update Status",
                        onClick = {
                            orderToEdit = order
                        },
                        isNotLoading = !state.isLoading
                    )
                OrderDetailCard(
                    title = "Order Items",
                    content = {
                        Column {
                            order.orderItems.forEachIndexed { index, orderItem ->
                                OrderItemCard(orderItem)
                                if (index != order.orderItems.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = Dimens.SpacePadding),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant
                                    )
                                }
                            }
                        }
                    }
                )
                OrderDetailCard(
                    title = "Order Address Information",
                    content = {
                        OrderAddressCard(driver = order.driver, store = order.store , address = order.orderAddress)
                    }
                )
                OrderDetailCard(
                    title = "Order Summary",
                    content = {
                        OrderSummaryCard(order = order)
                    }
                )
                OrderDetailCard(
                    title = "Order Timeline",
                    content = {
                        OrderTimelineCard(orderHistory = histories)
                    }
                )
            }
        }
    }
    OrderStatusBottomSheet(
        isVisible = orderToEdit != null,
        onDismiss = {
            orderToEdit = null
        },
        onConfirm = {
            orderToEdit?.let {
                    order ->
                viewModel.updateStatus(order.id)
            }
            orderToEdit = null
        },
    )
}


