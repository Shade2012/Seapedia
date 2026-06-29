package com.example.seapedia.presentation.seller.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.query.AllOrderQuery
import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.domain.usecases.order.GetAllOrderUseCase
import com.example.seapedia.domain.usecases.order.UpdateHistoryUseCase
import com.example.seapedia.domain.usecases.wallet.GetRevenueUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSellerViewModel @Inject constructor(
    private val getRevenueUseCase: GetRevenueUseCase,
    private val getAllOrderUseCase: GetAllOrderUseCase,
    private val updateHistoryUseCase: UpdateHistoryUseCase
): ViewModel(){
    private val _state = MutableStateFlow(HomeSellerScreenState())
    val state = _state.asStateFlow()

    init {
        initLoadData()
    }

    fun updateStatus(id: Int){
        viewModelScope.launch {
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
                        updateState {
                            copy(
                                orders = CommonState.Loading()
                            )
                        }
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
                        initLoadData()
                    }
                }
            }
        }
    }
    fun refresh(){
        loadData(true)
    }
    fun initLoadData(){
        loadData(false)
    }
    fun loadData(isRefresh: Boolean) {
        viewModelScope.launch {
            updateState {
                copy(isRefreshing = isRefresh)
            }
            try {
                coroutineScope {
                    launch {
                        getRevenueUseCase.run().collect { result ->
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
                                        copy(income = result)
                                    }
                                }

                                is CommonState.Loading<*> -> {
                                    updateState {
                                        copy(income = CommonState.Loading())
                                    }
                                }

                                is CommonState.Success<*> -> {
                                    updateState {
                                        copy(income = result)
                                    }
                                }
                            }
                        }
                    }

                    launch {
                        getAllOrderUseCase.run(
                            AllOrderQuery(
                                orderStatus = OrderStatus.PROCCESS
                            )
                        ).collect { result ->
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
                                        copy(orders = result)
                                    }
                                }

                                is CommonState.Loading<*> -> {
                                    updateState {
                                        copy(orders = CommonState.Loading())
                                    }
                                }

                                is CommonState.Success<*> -> {
                                    updateState {
                                        copy(orders = result)
                                    }
                                }
                            }
                        }
                    }
                }
            } finally {
                updateState {
                    copy(isRefreshing = false)
                }
            }
        }
    }

    private fun updateState(
        update: HomeSellerScreenState.() -> HomeSellerScreenState
    ){
        _state.update {
            it.update()
        }
    }
}