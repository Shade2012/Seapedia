package com.example.seapedia.presentation.buyer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.cart.CartItemBodySend
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.query.AllReviewQuery
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.usecases.carts.DeleteCartUseCase
import com.example.seapedia.domain.usecases.carts.UpdateCartUseCase
import com.example.seapedia.domain.usecases.product.GetAllProductUseCase
import com.example.seapedia.domain.usecases.review.GetAllReviewUseCase
import com.example.seapedia.domain.usecases.wallet.GetRevenueUseCase
import com.example.seapedia.domain.usecases.wallet.GetWalletUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.cartitems.CartItemRepository
import com.example.seapedia.global.utils.session.SessionRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBuyerViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val getAllProductUseCase: GetAllProductUseCase,
    private val getAllReviewUseCase: GetAllReviewUseCase,
    private val getWalletUseCase: GetWalletUseCase,
    private val getRevenueUseCase: GetRevenueUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val cartItemRepository: CartItemRepository
) : ViewModel() {
    private val _navigateToBuyer = MutableSharedFlow<Unit>()
    private val _state = MutableStateFlow(HomeState(searchName = ""))
    val sessionState = sessionRepository.sessionState
    val cartItemStateGlobal = cartItemRepository.cartItemsState
    val state = _state.asStateFlow()

    init {
        observeProduct()
        getWalletBalance()
        getReviews()
    }
    fun quantityCheckInCart(productId: Int) : Int {
        return cartItemRepository.checkIsExistInCartItem(productId)
    }

    fun getWalletBalance() {
        viewModelScope.launch {
            launch {
                getRevenueUseCase.run().collect { result ->
                    updateState {
                        copy(spending = result)
                    }
                }
            }

            launch {
                getWalletUseCase.run().collect { result ->
                    updateState {
                        copy(wallet = result)
                    }
                }
            }
        }
    }
    fun addToCart(): Boolean {
        if (sessionState.value.isValidBuyer) {
            return true
        }

        viewModelScope.launch {
            AppEventBus.events.emit(
                UiEvent.ShowSnackbar(
                    CustomSnackbarVisuals(
                        message = "Please fill your information in profile first",
                        type = SnackbarType.ERROR
                    )
                )
            )
        }

        return false
    }
    fun refreshHome() {
        viewModelScope.launch {

            updateState {
                copy(isRefreshing = true)
            }

            coroutineScope {
                launch { getWalletBalance() }
                launch { refreshReviews() }
                launch { refreshProducts() }
            }

            updateState {
                copy(isRefreshing = false)
            }
        }
    }
    fun onSearchNameChange(searchName: String){
        updateState {
            copy(searchName = searchName)
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


    private fun updateState(
        update: HomeState.() -> HomeState
    ) {
        _state.update {
            it.update()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeProduct(){
        viewModelScope.launch {
            state.map {
                it.searchName
            }
                .debounce(400)
                .distinctUntilChanged()
                .flatMapLatest {
                    searchName ->
                    getAllProductUseCase.run(
                        AllProductQuery(name = searchName)
                    )
                }.collect {
                    result ->
                    when(result){
                        is CommonState.Error<*> -> {
                            updateState {
                                copy(
                                    products = CommonState.Error(result.message)
                                )
                            }
                            AppEventBus.events.emit(
                                UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                    message = "Failed get products, ${result.message}",
                                    type = SnackbarType.ERROR
                                ))
                            )
                        }
                        is CommonState.Loading<*> -> {
                            updateState {
                                copy(products = CommonState.Loading())
                            }
                        }
                        is CommonState.Success<*> -> {
                            updateState {
                                copy(products=result)
                            }
                        }
                    }
                }
        }
    }

    private fun getReviews(){
        viewModelScope.launch {
            getAllReviewUseCase.run(queries = AllReviewQuery(limit = 3)).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(reviews = result)
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(reviews = CommonState.Loading())
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState {
                            copy(reviews = result)
                        }
                    }
                }
            }
        }
    }

    private suspend fun refreshProducts() {
        val searchName = state.value.searchName

        getAllProductUseCase
            .run(AllProductQuery(name = searchName))
            .collect { result ->

                updateState {
                    copy(products = result)
                }
            }
    }
    private suspend fun refreshReviews() {
        getAllReviewUseCase
            .run(AllReviewQuery(limit = 3))
            .collect { result ->

                updateState {
                    copy(reviews = result)
                }
            }
    }
}