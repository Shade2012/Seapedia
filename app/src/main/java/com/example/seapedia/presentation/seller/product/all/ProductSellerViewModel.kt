package com.example.seapedia.presentation.seller.product.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.query.AllProductSellerQuery
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.usecases.product.GetAllSellerProductUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSellerViewModel
    @Inject constructor(
        private val getAllSellerProductUseCase: GetAllSellerProductUseCase
    ) : ViewModel() {
        private val _state = MutableStateFlow<ProductSellerState>(ProductSellerState())
        val state = _state.asStateFlow()

    init {
        observeProduct()
    }

    fun onDelete(product: ProductEntity){

    }
    fun getProducts(){
        viewModelScope.launch {
            updateState {
                copy(data = CommonState.Loading())
            }
            getAllSellerProductUseCase.run(AllProductSellerQuery()).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        AppEventBus.events.emit(UiEvent.ShowSnackbar(
                            data = CustomSnackbarVisuals(
                                message = "Failed to get products",
                                type = SnackbarType.ERROR
                            )
                        ))
                        updateState {
                            copy(
                                data = result
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(data = result)
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState {
                            copy(data = result)
                        }
                    }
                }
            }
        }
    }
    fun refreshProduct(){
        viewModelScope.launch {
            updateState {
                copy(
                    data = CommonState.Loading(),
                    isRefreshing = true
                )
            }
            getAllSellerProductUseCase.run(AllProductSellerQuery(name = state.value.searchName)).collect {
                    result ->
                when(result){
                    is CommonState.Error<*> -> {
                        AppEventBus.events.emit(UiEvent.ShowSnackbar(
                            data = CustomSnackbarVisuals(
                                message = "Failed to get products",
                                type = SnackbarType.ERROR
                            )
                        ))
                        updateState {
                            copy(
                                data = result,
                                isRefreshing = false
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(data = result,isRefreshing = true)
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState {
                            copy(data = result,isRefreshing = false)
                        }
                    }
                }
            }
        }
    }
    fun onSearchNameChange(searchName: String){
        updateState {
            copy(searchName = searchName)
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
                    getAllSellerProductUseCase.run(
                        AllProductSellerQuery(name = searchName)
                    )
                }.collect {
                        result ->
                    when(result){
                        is CommonState.Error<*> -> {
                            updateState {
                                copy(
                                    data = CommonState.Error(result.message)
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
                                copy(data = CommonState.Loading())
                            }
                        }
                        is CommonState.Success<*> -> {
                            updateState {
                                copy(data=result)
                            }
                        }
                    }
                }
        }
    }


    private fun updateState(
        update: ProductSellerState.() -> ProductSellerState
    ) {
        _state.update {
            it.update()
        }
    }
}