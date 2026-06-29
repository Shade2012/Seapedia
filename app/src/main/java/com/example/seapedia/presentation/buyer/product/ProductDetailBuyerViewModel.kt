package com.example.seapedia.presentation.buyer.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.responses.carts.CartItemResponse
import com.example.seapedia.data.remote.responses.carts.CartResponse
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.toCartItemResponse
import com.example.seapedia.domain.usecases.carts.DeleteCartUseCase
import com.example.seapedia.domain.usecases.carts.UpdateCartUseCase
import com.example.seapedia.domain.usecases.product.GetDetailProductUseCase
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.cartitems.CartItemRepository
import com.example.seapedia.global.utils.session.SessionRepository
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
class ProductDetailBuyerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailProductUseCase: GetDetailProductUseCase,
    private val sessionRepository: SessionRepository,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val cartItemRepository: CartItemRepository
): ViewModel(){
    private val _state : MutableStateFlow<ProductDetailBuyerState> = MutableStateFlow(ProductDetailBuyerState())
    val cartItemStateGlobal = cartItemRepository.cartItemsState
    val state = _state.asStateFlow()

    val sessionState = sessionRepository.sessionState

    fun quantityCheckInCart(productId: Int) : Int {
        return cartItemRepository.checkIsExistInCartItem(productId)
    }

    private val productId: Int = checkNotNull(savedStateHandle[BuyerRoute.ProductDetail.PRODUCT_ID])

    init {
        getProductDetail(productId)
    }

    fun onClickDetailImage(url: String){
        updateState {
            copy(selectedImage = url)
        }
    }


    fun onDecrement(
        quantity: Int,
        cartItemId: Int
    ) {
        viewModelScope.launch {
            updateState {
                copy(bottomSheetLoading = true)
            }
            val request =
                if (quantity - 1 == 0) {
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
                onRun = {
                    cartItemRepository.decrementQuantity(cartItemId)
                }
            )
            updateState {
                copy(
                    bottomSheetLoading = false
                )
            }
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
            updateState {
                copy(
                    bottomSheetLoading = true
                )
            }
            executeCartAction(
                request = updateCartUseCase.run(
                    cartItemId,
                    CartItemBodySend(
                        quantity = quantity + 1
                    )
                ),
                onRun = {
                    cartItemRepository.addQuantity(cartItemId)
                }
            )
            updateState {
                copy(
                    bottomSheetLoading = false
                )
            }
        }
    }

    private suspend fun executeCartAction(
        request: Flow<CommonState<String>>,
        onRun:()-> Unit,
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
                }

                is CommonState.Success -> {
                    onRun()
                }
            }
        }
    }

    private fun getProductDetail(id: Int){
        viewModelScope.launch {
            getDetailProductUseCase.run(id).collect {
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
                                selectedImage = result.data.listImages.firstOrNull()?.imageUrl
                            )
                        }
                    }
                }
            }
        }
    }
    private fun updateState(
        update: ProductDetailBuyerState.() -> ProductDetailBuyerState
    ) {
        _state.update {
            it.update()
        }
    }

}