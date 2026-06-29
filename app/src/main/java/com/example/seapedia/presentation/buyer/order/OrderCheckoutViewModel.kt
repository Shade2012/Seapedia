package com.example.seapedia.presentation.buyer.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.body.order.OrderBody
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.data.remote.responses.order.DeliveryMethod
import com.example.seapedia.domain.entities.Address
import com.example.seapedia.domain.usecases.address.GetAllAddressUseCase
import com.example.seapedia.domain.usecases.carts.DeleteCartUseCase
import com.example.seapedia.domain.usecases.carts.GetCartUseCase
import com.example.seapedia.domain.usecases.carts.UpdateCartUseCase
import com.example.seapedia.domain.usecases.order.CheckoutOrderUseCase
import com.example.seapedia.domain.usecases.order.GetPreviewUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.cart_items.CartItemRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderCheckoutViewModel @Inject constructor(
    private val getPreviewUseCase: GetPreviewUseCase,
    private val getAllAddressUseCase: GetAllAddressUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val checkoutOrderUseCase: CheckoutOrderUseCase,
    private val cartItemRepository: CartItemRepository,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
) : ViewModel(){
    private val _state = MutableStateFlow(OrderCheckoutState())
    val state = _state.asStateFlow()

    private val _navigateToCart = MutableSharedFlow<Unit>()
    val navigateToCart = _navigateToCart.asSharedFlow()

    init {
        onInit(false)
    }
    fun onRefresh(){
        onInit(true)
    }

    fun checkout(){
        viewModelScope.launch {
            updateState {
                copy(
                    isConfirmLoading = true
                )
            }
            checkoutOrderUseCase.run(
                OrderBody(
                    voucherCode = state.value.voucherCode,
                    addressId = state.value.selectedAddress!!.id,
                    deliveryMethod = state.value.selectedDeliveryMethod
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
                            copy(
                                isConfirmLoading =false,
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                isConfirmLoading = true
                            )
                        }
                    }
                    is CommonState.Success -> {
                        cartItemRepository.clearCart()
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    message = result.data,
                                    type = SnackbarType.SUCCESS
                                )
                            )
                        )
                        updateState {
                            copy(
                                isConfirmLoading = false,
                            )
                        }
                        _navigateToCart.emit(Unit)
                    }
                }
            }
        }
    }

    fun onChangeAddress(address: Address){
        updateState {
            copy(
                selectedAddress = address
            )
        }
        onInit(false)
    }

    fun onChangeVoucherCode(value:String){
        updateState {
            copy(
                voucherCode = value
            )
        }
    }

    fun onDecrement(
        quantity: Int,
        cartItemId: Int
    ) {
        viewModelScope.launch {

            val previousCart = state.value.cart

            updateState {
                copy(cart = CommonState.Loading())
            }

            val request =
                if (quantity == 0) {
                    deleteCartUseCase.run(cartItemId)
                } else {
                    updateCartUseCase.run(
                        cartItemId,
                        CartItemBodySend(
                            quantity = quantity - 1
                        )
                    )
                }

            executeCartAction(
                request = request,
                previousCart = previousCart
            )
        }
    }

    fun onIncrement(
        quantity: Int,
        cartItemId: Int,
        stock: Int
    ) {
        if (quantity >= stock) {
            viewModelScope.launch {
                AppEventBus.events.emit(
                    UiEvent.ShowSnackbar(
                        CustomSnackbarVisuals(
                            type = SnackbarType.ERROR,
                            message = "Product stock is insufficient."
                        )
                    )
                )
            }
            return
        }

        viewModelScope.launch {

            val previousCart = state.value.cart

            updateState {
                copy(cart = CommonState.Loading())
            }

            executeCartAction(
                request = updateCartUseCase.run(
                    cartItemId,
                    CartItemBodySend(
                        quantity = quantity + 1
                    )
                ),
                previousCart = previousCart
            )

        }
    }

    private suspend fun executeCartAction(
        request: Flow<CommonState<String>>,
        previousCart: CommonState<CartResponse>
    ) {
        request.collect { result ->

            when (result) {

                is CommonState.Loading -> {}

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
                        copy(cart = previousCart)
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
                    onCartLoad()
                    onLoadPreview()
                }
            }
        }
    }

    fun onChangeDeliveryMethod(
        method: DeliveryMethod
    ){
        updateState {
            copy(
                selectedDeliveryMethod = method
            )
        }
    }
    private fun onCartLoad() {
        viewModelScope.launch {
            getCartUseCase.run().collect { result ->
                if (result is CommonState.Success) {
                    cartItemRepository.replaceCartItems(result.data.cartItems)

                    updateState {
                        copy(cart = result)
                    }
                }
            }
        }
    }

    fun onInit(isRefreshing: Boolean) {
        viewModelScope.launch {
            updateState {
                copy(
                    isRefreshing = isRefreshing,
                    isLoading = true
                )
            }

            coroutineScope {

                val addressJob = async {
                    getAllAddressUseCase.run().last()
                }

                val cartJob = async {
                    getCartUseCase.run().last()
                }

                val addressResult = addressJob.await()
                val cartResult = cartJob.await()

                if(cartResult is CommonState.Success){
                    cartItemRepository.replaceCartItems(cartResult.data.cartItems)
                }
                updateState {
                    copy(
                        listAddress = addressResult,
                        cart = cartResult
                    )
                }

                if (addressResult is CommonState.Success) {
                    val selectedAddress = state.value.selectedAddress ?: addressResult.data.firstOrNull() ?: return@coroutineScope
                    updateState {
                        copy(
                            selectedAddress = selectedAddress
                        )
                    }
                    onLoadPreview(selectedAddress.id)
                }
            }
            updateState {
                copy(
                    isRefreshing = false,
                    isLoading = false
                )
            }
        }
    }

    private fun onLoadPreview(addressId :Int? = null){
        viewModelScope.launch {
            getPreviewUseCase.run(
                OrderBody(
                    deliveryMethod = DeliveryMethod.REGULAR,
                    addressId = addressId ?: state.value.selectedAddress!!.id
                )
            ).collect { result ->
                updateState {
                    copy(
                        selectedAddress = selectedAddress,
                        orderPreviewResponse = result
                    )
                }
            }
        }
    }
    private fun updateState(
        updateState: OrderCheckoutState.() -> OrderCheckoutState
    ){
        _state.update {
            it.updateState()
        }
    }
}