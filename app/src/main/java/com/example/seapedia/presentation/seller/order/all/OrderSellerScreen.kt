package com.example.seapedia.presentation.seller.order.all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.auth.login.widgets.DropdownCustom
import com.example.seapedia.presentation.common.EmptyCommonCustom
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.RefreshCommon
import com.example.seapedia.presentation.seller.order.all.shimmer.OrderAllShimmer
import com.example.seapedia.presentation.seller.order.all.widgets.OrderCard
import com.example.seapedia.presentation.seller.order.all.widgets.orderListSection
import com.example.seapedia.presentation.seller.order.widgets.OrderStatusBottomSheet
import com.example.seapedia.ui.theme.Dimens
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun OrderSellerScreen(
    sellerNavController: NavController,
    modifier: Modifier = Modifier,
    viewModel: OrderSellerViewModel = hiltViewModel()
) {
    val role = viewModel.sessionState.value.role ?: UserRole.Guest
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val listState = rememberLazyGridState()
    var orderToEdit by rememberSaveable {
        mutableStateOf<Order?>(null)
    }
    val refresh = sellerNavController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_order",false)
    LaunchedEffect(Unit) {
        refresh?.collect {
                shouldRefresh ->
            if(shouldRefresh){
                viewModel.refreshOrder()
                sellerNavController.currentBackStackEntry?.savedStateHandle?.set("refresh_product",false)
            }
        }
    }
    RefreshCommon (
        modifier = modifier,
        refreshing = state.isRefreshing,
        onRefresh= viewModel::refreshOrder
    ){
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            state = listState,
            contentPadding = PaddingValues(Dimens.InnerPadding),
            horizontalArrangement = Arrangement.spacedBy(Dimens.RowSpacePadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {
            when (val result = state.allOrders) {
                is CommonState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        OrderAllShimmer()
                    }
                }

                is CommonState.Error -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        FailedCommonCustom(text = result.message)
                    }
                }

                is CommonState.Success -> {
                    val searching = state.status != OrderStatus.All
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        DropdownCustom(
                            list = orderStatusList,
                            label = "",
                            selectedItem = state.status,
                            onItemSelected = {
                                viewModel.filterByStatus(it)
                            },
                            itemLabel = { status ->
                                status.displayName
                            },
                            isLoading = false,
                        )
                    }

                    if (result.data.isEmpty()) {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            EmptyCommonCustom(text = "No Order yet")
                        }
                    }


                    if (searching) {
                        items(
                            items = result.data,
                            key = { it.id }
                        ) { order ->
                            OrderCard(
                                order = order,
                                onClick = {
                                    sellerNavController.navigate(SellerRoute.OrderDetail.createRoute(order.id))
                                },
                                role = role,
                                onUpdateStatus = {
                                    orderToEdit = order
                                },
                                daySystem = state.daySystem ?: Clock.System.now()
                            )
                        }
                    } else {
                        orderListSection(
                            title = "Proccess",
                            orders = state.processOrder,
                            sellerNavController = sellerNavController,
                            role = role,
                            onUpdate = {
                             orderToEdit = it
                            },
                            daySystem = state.daySystem ?: Clock.System.now()
                        )

                        orderListSection(
                            title = "Waiting Driver",
                            orders = state.waitingDriverOrder,
                            sellerNavController = sellerNavController,
                            role = role,
                            onUpdate = {
                                orderToEdit = it
                            },
                            daySystem = state.daySystem ?: Clock.System.now()
                        )

                        orderListSection(
                            title = "On Way",
                            orders = state.onWayOrder,
                            sellerNavController = sellerNavController,
                            role = role,
                            onUpdate = {
                                orderToEdit = it
                            },
                            daySystem = state.daySystem ?: Clock.System.now()
                        )

                        orderListSection(
                            title = "Done",
                            orders = state.doneOrder,
                            sellerNavController = sellerNavController,
                            role = role,
                            onUpdate = {
                                orderToEdit = it
                            },
                            daySystem = state.daySystem ?: Clock.System.now()
                        )

                        orderListSection(
                            title = "Returned",
                            orders = state.returnOrder,
                            sellerNavController = sellerNavController,
                            role = role,
                            onUpdate = {
                                orderToEdit = it
                            },
                            daySystem = state.daySystem ?: Clock.System.now()
                        )
                    }
                }
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







