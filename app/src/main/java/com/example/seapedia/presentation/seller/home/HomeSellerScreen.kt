package com.example.seapedia.presentation.seller.home

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.common.BalanceSection
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.seller.home.shimmer.BalanceSectionShimmer
import com.example.seapedia.presentation.seller.home.shimmer.OrderSectionShimmer
import com.example.seapedia.presentation.seller.home.widgets.HomeOrderListSection
import com.example.seapedia.presentation.seller.order.widgets.OrderStatusBottomSheet
import com.example.seapedia.presentation.seller.widgets.SellerBody
import com.example.seapedia.ui.theme.ErrorColor
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun HomeSellerScreen(
    rootNavController: NavController,
    navController: NavController,
    viewModel: HomeSellerViewModel = hiltViewModel<HomeSellerViewModel>()
) {
    val role = viewModel.sessionState.value.role ?: UserRole.Guest
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var orderToEdit by rememberSaveable {
        mutableStateOf<Order?>(null)
    }

    val scrollState = rememberScrollState()
    SellerBody(
        scrollState,
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::refresh
    ) {
        when (state.income) {
            is CommonState.Error<*> -> {
                Text(
                    text = state.income.message, style = MaterialTheme.typography.bodyMedium.copy(
                        color = ErrorColor
                    )
                )
            }

            is CommonState.Loading<*> -> {
                BalanceSectionShimmer()
            }

            is CommonState.Success -> {
                BalanceSection(
                    title= "Income",
                    amount = state.income.data,
                    onClick = {
                        rootNavController.navigate(NavGraph.WALLET_TRANSACTIONS)
                    }
                )
            }
        }
        when (state.orders) {
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = state.orders.message)
            }

            is CommonState.Loading<*> -> {
                OrderSectionShimmer()
            }

            is CommonState.Success -> {
                HomeOrderListSection(
                    orders = state.orders.data,
                    onClick = {
                        rootNavController.navigate(SellerRoute.OrderDetail.createRoute(it.id))
                    },
                    daySystem = state.daySystem ?: Clock.System.now(),
                    role = role,
                    onUpdateStatus = {
                        orderToEdit = it
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
            orderToEdit?.let { order ->
                viewModel.updateStatus(order.id)
            }
            orderToEdit = null
        },
    )
}