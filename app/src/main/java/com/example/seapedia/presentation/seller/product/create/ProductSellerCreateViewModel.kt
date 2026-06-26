package com.example.seapedia.presentation.seller.product.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.product.GetAllSellerProductUseCase
import com.example.seapedia.global.utils.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSellerCreateViewModel
    @Inject constructor(
        private val getAllSellerProductUseCase: GetAllSellerProductUseCase
    ) : ViewModel() {
        private val _state = MutableStateFlow<ProductSellerCreateState>(ProductSellerCreateState())
        val state = _state.asStateFlow()

    init {
        getProducts()
    }
    private fun getProducts(){
        viewModelScope.launch {
            updateState {
                copy(products = CommonState.Loading())
            }
            getAllSellerProductUseCase.run().collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(
                                products = result
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(products = result)
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState {
                            copy(products = result)
                        }
                    }
                }
            }
        }
    }

    private fun updateState(
        update: ProductSellerCreateState.() -> ProductSellerCreateState
    ) {
        _state.update {
            it.update()
        }
    }
}