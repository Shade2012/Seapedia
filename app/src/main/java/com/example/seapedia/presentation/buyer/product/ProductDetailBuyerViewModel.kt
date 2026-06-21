package com.example.seapedia.presentation.buyer.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.usecases.product.GetDetailProductUseCase
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailBuyerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailProductUseCase: GetDetailProductUseCase
): ViewModel(){
    private val _state : MutableStateFlow<ProductDetailBuyerState> = MutableStateFlow(ProductDetailBuyerState())
    val state = _state.asStateFlow()
    private val productId: Int = checkNotNull(savedStateHandle[BuyerRoute.ProductDetail.PRODUCT_ID])

    init {
        getProductDetail(productId)
    }

    fun onClickDetailImage(url: String){
        updateState {
            copy(selectedImage = url)
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
                    is CommonState.Success<ProductEntity> -> {
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