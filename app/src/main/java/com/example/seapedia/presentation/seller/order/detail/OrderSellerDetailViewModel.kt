package com.example.seapedia.presentation.seller.order.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.order.GetDetailOrderUseCase
import com.example.seapedia.domain.usecases.order.GetOrderHistoriesUseCase
import com.example.seapedia.domain.usecases.order.UpdateHistoryUseCase
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderSellerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailOrderUseCase: GetDetailOrderUseCase,
    private val getOrderHistoriesUseCase: GetOrderHistoriesUseCase,
    private val updateHistoryUseCase: UpdateHistoryUseCase,
): ViewModel(){
    private val _state = MutableStateFlow<OrderSellerDetailState>(OrderSellerDetailState())
    val state = _state.asStateFlow()

    private val _refreshOrderAll = MutableSharedFlow<Unit>()
    val refreshOrderAll = _refreshOrderAll.asSharedFlow()
    private val orderId: Int = checkNotNull(savedStateHandle[SellerRoute.OrderDetail.ORDER_ID])
    init {
        getDetail()
    }

    fun updateStatus(id: Int){
        viewModelScope.launch {
            updateState {
                copy(
                    order = CommonState.Loading(),
                    orderHistories = CommonState.Loading()
                )
            }
            updateHistoryUseCase.run(id).collect {
                    result ->
                when(result){
                    is CommonState.Error -> {
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.ERROR,
                                    message = result.message
                                )
                            )
                        )
                    }
                    is CommonState.Loading<*> -> {
                    }
                    is CommonState.Success -> {
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.SUCCESS,
                                    message = result.data
                                )
                            )
                        )
                        _refreshOrderAll.emit(Unit)

                    }
                }
            }
            getDetail()
        }
    }
    fun refreshDetail() {
        loadDetail(isRefreshing = true)
    }

    private fun getDetail() {
        loadDetail(isRefreshing = false)
    }

    private fun loadDetail(isRefreshing: Boolean) {
        viewModelScope.launch {

            if (isRefreshing) {
                updateState {
                    copy(isRefreshing = true)
                }
            }

            launch {
                getDetailOrderUseCase.run(orderId).collect { result ->
                    when (result) {
                        is CommonState.Error<*> -> {
                            AppEventBus.events.emit(
                                UiEvent.ShowSnackbar(
                                    CustomSnackbarVisuals(
                                        type = SnackbarType.ERROR,
                                        message = result.message
                                    )
                                )
                            )
                            updateState {
                                copy(
                                    order = result,
                                    isRefreshing = false
                                )
                            }
                        }

                        is CommonState.Loading<*> -> {
                            if (!isRefreshing) {
                                updateState {
                                    copy(order = result)
                                }
                            }
                        }

                        is CommonState.Success -> {
                            updateState {
                                copy(
                                    order = result,
                                    isRefreshing = false
                                )
                            }
                        }
                    }
                }
            }

            launch {
                getOrderHistoriesUseCase.run(orderId).collect { result ->
                    when (result) {
                        is CommonState.Error<*> -> {
                            AppEventBus.events.emit(
                                UiEvent.ShowSnackbar(
                                    CustomSnackbarVisuals(
                                        type = SnackbarType.ERROR,
                                        message = result.message
                                    )
                                )
                            )
                            updateState {
                                copy(
                                    orderHistories = result,
                                    isRefreshing = false
                                )
                            }
                        }

                        is CommonState.Loading<*> -> {
                            if (!isRefreshing) {
                                updateState {
                                    copy(orderHistories = result)
                                }
                            }
                        }

                        is CommonState.Success -> {
                            updateState {
                                copy(
                                    orderHistories = result,
                                    isRefreshing = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    private fun updateState(
        update: OrderSellerDetailState.() -> OrderSellerDetailState
    ) {
        _state.update {
            it.update()
        }
    }
}