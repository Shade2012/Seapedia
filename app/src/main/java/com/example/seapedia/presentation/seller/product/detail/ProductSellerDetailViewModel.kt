package com.example.seapedia.presentation.seller.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.usecases.product.GetDetailProductUseCase
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import com.example.seapedia.presentation.buyer.product.ProductDetailBuyerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSellerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailProductUseCase: GetDetailProductUseCase
) : ViewModel() {

    private val _state : MutableStateFlow<ProductSellerDetailState> = MutableStateFlow(ProductSellerDetailState())
    val state = _state.asStateFlow()
    private val productId: Int = checkNotNull(savedStateHandle[SellerRoute.ProductDetail.PRODUCT_ID])

    init {
        getProductDetail(productId)
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
                    is CommonState.Success<ProductEntity> -> {
                        updateState {
                            copy(
                                product = result,
                            )
                        }
                    }
                }
            }
        }
    }
    private fun updateState(
        update: ProductSellerDetailState.() -> ProductSellerDetailState
    ) {
        _state.update {
            it.update()
        }
    }
}