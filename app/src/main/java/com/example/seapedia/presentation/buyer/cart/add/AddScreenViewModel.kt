package com.example.seapedia.presentation.buyer.cart.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.responses.carts.CartItemResponse
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.toCartItemFormState
import com.example.seapedia.domain.usecases.carts.CreateCartItemUseCase
import com.example.seapedia.domain.usecases.product.GetDetailProductUseCase
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.cartitems.CartItemRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import com.example.seapedia.presentation.buyer.cart.state.toBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailProductUseCase: GetDetailProductUseCase,
    private val createCartItemUseCase: CreateCartItemUseCase,
    private val cartItemRepository: CartItemRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddCartItemState())
    val state = _state.asStateFlow()

    private val _navigateBackStack = MutableSharedFlow<Unit>()
    val navigateBackStack = _navigateBackStack.asSharedFlow()

    private val productId: Int = checkNotNull(savedStateHandle[BuyerRoute.CartItemCreate.PRODUCT_ID])

    init {
        getProductDetail()
    }
    fun selectSingleType(
        productTypeId: Int,
        itemId: Int
    ) {
        updateState {
            copy(
                state = state.copy(
                    selectedTypes = state.selectedTypes.map { type ->
                        if (type.productTypeId == productTypeId) {
                            type.copy(
                                selectedItemIds = listOf(itemId)
                            )
                        } else {
                            type
                        }
                    }
                )
            )
        }
    }

    fun toggleMultipleType(
        productTypeId: Int,
        itemId: Int
    ) {
        updateState {
            copy(
                state = state.copy(
                    selectedTypes = state.selectedTypes.map { type ->

                        if (type.productTypeId != productTypeId) {
                            type
                        } else {

                            val newSelection =
                                if (itemId in type.selectedItemIds) {
                                    type.selectedItemIds - itemId
                                } else {
                                    type.selectedItemIds + itemId
                                }

                            type.copy(
                                selectedItemIds = newSelection
                            )
                        }
                    }
                )
            )
        }
    }

    fun submit() {
        viewModelScope.launch {
            createCartItemUseCase
                .run(state.value.state.toBody())
                .collect { result ->

                    when (result) {

                        is CommonState.Loading -> {
                            updateState {
                                copy(isLoading = true)
                            }
                        }

                        is CommonState.Error -> {

                            updateState {
                                copy(isLoading = false)
                            }

                            AppEventBus.events.emit(
                                UiEvent.ShowSnackbar(
                                    CustomSnackbarVisuals(
                                        message = result.message,
                                        type = SnackbarType.ERROR
                                    )
                                )
                            )
                        }

                        is CommonState.Success -> {

                            updateState {
                                copy(isLoading = false)
                            }

                            AppEventBus.events.emit(
                                UiEvent.ShowSnackbar(
                                    CustomSnackbarVisuals(
                                        message = "Cart item added successfully",
                                        type = SnackbarType.SUCCESS
                                    )
                                )
                            )
                            cartItemRepository.onReload()
                            _navigateBackStack.emit(Unit)
                        }
                    }
                }
        }
    }
    fun decreaseQuantity() {
        updateState {
            copy(
                state = state.copy(
                    quantity = maxOf(1, state.quantity - 1)
                )
            )
        }
    }
    fun increaseQuantity() {
        updateState {
            copy(
                state = state.copy(
                    quantity = state.quantity + 1
                )
            )
        }
    }
    private fun getProductDetail(){
        viewModelScope.launch {
            getDetailProductUseCase.run(productId).collect {
                    result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(
                                product = CommonState.Error(result.message)
                            )
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = "Faild get product detail, ${result.message}",
                                type = SnackbarType.ERROR
                            ))
                        )
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(product = CommonState.Loading())
                        }

                    }
                    is CommonState.Success<Product> -> {
                        updateState {
                            copy(
                                product = result,
                                state = result.data.toCartItemFormState()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateState(
        updateState: AddCartItemState.() -> AddCartItemState
    ){
        _state.update {
            it.updateState()
        }
    }

}