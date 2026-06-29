package com.example.seapedia.presentation.buyer.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.domain.usecases.carts.ClearCartUseCase
import com.example.seapedia.domain.usecases.carts.DeleteCartUseCase
import com.example.seapedia.domain.usecases.carts.GetCartUseCase
import com.example.seapedia.domain.usecases.carts.UpdateCartUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.cartitems.CartItemRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val cartItemRepository: CartItemRepository,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<CartScreenState>(CartScreenState())
    val state = _state.asStateFlow()

    fun onClearCart() {
        viewModelScope.launch {
            val previousCart = state.value.cart
            updateState {
                copy(cart = CommonState.Loading())
            }
            executeCartAction(
                request = clearCartUseCase.run(),
                previousCart = previousCart
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

    init {
        onInit()
    }

    fun onRefresh(){
        onInit(true)
    }
    fun onInit(isRefreshing: Boolean = false){
        viewModelScope.launch {
            updateState {
                copy(
                    isRefreshing = isRefreshing
                )
            }
            getCartUseCase.run().collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(
                                cart = result
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                cart=result
                            )
                        }
                    }
                    is CommonState.Success -> {
                        updateState {
                            copy(
                                cart =result
                            )
                        }
                        cartItemRepository.replaceCartItems(result.data.cartItems)
                    }
                }
            }
            updateState {
                copy(
                    isRefreshing = false
                )
            }
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
                    onInit()
                }
            }
        }
    }
    private fun updateState(
        updateState: CartScreenState.() -> CartScreenState
    ){
        _state.update {
            it.updateState()
        }
    }
}