package com.example.seapedia.presentation.seller.order.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.query.AllOrderQuery
import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.domain.usecases.order.GetAllOrderUseCase
import com.example.seapedia.domain.usecases.order.UpdateHistoryUseCase
import com.example.seapedia.domain.usecases.system.GetDayUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.session.SessionRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.groupBy
import kotlin.collections.orEmpty
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@HiltViewModel
class OrderSellerViewModel @Inject constructor(
    val sessionRepository: SessionRepository,
    private val getAllOrderUseCase: GetAllOrderUseCase,
    private val updateHistoryUseCase: UpdateHistoryUseCase,
    private val getDayUseCase: GetDayUseCase
) : ViewModel(){
    private val _state = MutableStateFlow<OrderSellerState>(OrderSellerState())
    val state = _state.asStateFlow()

    val sessionState = sessionRepository.sessionState

    init {
        getOrders()
    }

    fun updateStatus(id: Int){
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true
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
                        refreshOrder()
                    }
                }
            }
        }
    }
    fun filterByStatus(orderStatus: OrderStatus){
        viewModelScope.launch {
            updateState {
                copy(
                    isLoading = true,
                    status = orderStatus
                )
            }
            val status = if(state.value.status == OrderStatus.All) null else state.value.status
            val daySystem = getDayUseCase.run()
                .filterIsInstance<CommonState.Success<Instant>>()
                .first().data

            getAllOrderUseCase.run(
                AllOrderQuery(
                    orderStatus = status
                )
            ).collect {
                result ->
                when(result){
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
                            copy(allOrders = result,
                                isLoading = false)
                        }
                    }
                    is CommonState.Loading -> {
                        updateState {
                            copy(
                                allOrders = result
                            )
                        }
                    }
                    is CommonState.Success -> {
                        val orders = result.data
                        val grouped = orders.groupBy { it.status }
                        val returnOrder = grouped[OrderStatus.RETURN].orEmpty()
                        val doneOrder = grouped[OrderStatus.DONE].orEmpty()
                        val onWayOrder = grouped[OrderStatus.ON_WAY].orEmpty()
                        val waitingDriverOrder = grouped[OrderStatus.WAITING_DRIVER].orEmpty()
                        val processOrder = grouped[OrderStatus.PROCCESS].orEmpty()
                        updateState {
                            copy(
                                allOrders = result,
                                daySystem = daySystem,
                                returnOrder,
                                doneOrder,
                                onWayOrder,
                                waitingDriverOrder,
                                processOrder,
                                isLoading = false,

                            )
                        }
                    }
                }
            }

        }
    }
    fun refreshOrder(){
        viewModelScope.launch {
            updateState {
                copy(
                    allOrders = CommonState.Loading(),
                    isRefreshing = true,
                    isLoading = true
                )
            }
            val status = if(state.value.status == OrderStatus.All) null else state.value.status
            getAllOrderUseCase.run(AllOrderQuery(status)).collect {
                    result ->
                when(result){
                    is CommonState.Error<*> -> {
                        AppEventBus.events.emit(UiEvent.ShowSnackbar(
                            data = CustomSnackbarVisuals(
                                message = "Failed to get orders",
                                type = SnackbarType.ERROR
                            )
                        ))
                        updateState {
                            copy(
                                allOrders = result,
                                isRefreshing = false,
                                isLoading = false
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                allOrders = result,isRefreshing = true)
                        }
                    }
                    is CommonState.Success -> {
                        val orders = result.data
                        val grouped = orders.groupBy { it.status }
                        val returnOrder = grouped[OrderStatus.RETURN].orEmpty()
                        val doneOrder = grouped[OrderStatus.DONE].orEmpty()
                        val onWayOrder = grouped[OrderStatus.ON_WAY].orEmpty()
                        val waitingDriverOrder = grouped[OrderStatus.WAITING_DRIVER].orEmpty()
                        val processOrder = grouped[OrderStatus.PROCCESS].orEmpty()
                        updateState {
                            copy(
                                allOrders = result,
                                isRefreshing = false,
                                isLoading = false,
                                returnOrder = returnOrder,
                                doneOrder = doneOrder,
                                onWayOrder = onWayOrder,
                                waitingDriverOrder = waitingDriverOrder,
                                processOrder = processOrder,
                            )
                        }
                    }
                }
            }
        }
    }
    private fun getOrders(){
        viewModelScope.launch {
            updateState {
                copy(allOrders = CommonState.Loading())
            }
            val daySystem = getDayUseCase.run()
                .filterIsInstance<CommonState.Success<Instant>>()
                .first().data
            getAllOrderUseCase.run(queries = AllOrderQuery()).collect {
                result ->
                when(result){
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
                            copy(allOrders = result)
                        }
                    }
                    is CommonState.Loading -> {
                        updateState {
                            copy(allOrders = result)
                        }
                    }
                    is CommonState.Success -> {
                        val orders = result.data
                        val grouped = orders.groupBy { it.status }
                        val returnOrder = grouped[OrderStatus.RETURN].orEmpty()
                        val doneOrder = grouped[OrderStatus.DONE].orEmpty()
                        val onWayOrder = grouped[OrderStatus.ON_WAY].orEmpty()
                        val waitingDriverOrder = grouped[OrderStatus.WAITING_DRIVER].orEmpty()
                        val processOrder = grouped[OrderStatus.PROCCESS].orEmpty()
                        updateState {
                            copy(
                                allOrders = result,
                                daySystem = daySystem,
                                returnOrder,
                                doneOrder,
                                onWayOrder,
                                waitingDriverOrder,
                                processOrder,
                            )
                        }
                    }
                }
            }
        }
    }
    private fun updateState(
        update: OrderSellerState.() -> OrderSellerState
    ){
        _state.update {
            it.update()
        }
    }

}