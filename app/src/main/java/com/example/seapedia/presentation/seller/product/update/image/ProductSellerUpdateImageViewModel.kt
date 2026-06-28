package com.example.seapedia.presentation.seller.product.update.image

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.ProductImageBody
import com.example.seapedia.data.remote.query.ProductImagesQuery
import com.example.seapedia.domain.usecases.product.CreateProductImageUseCase
import com.example.seapedia.domain.usecases.product.DeleteProductImageUseCase
import com.example.seapedia.domain.usecases.product.GetAllProductImagesUseCase
import com.example.seapedia.global.navigation.seller.SellerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSellerUpdateImageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deleteProductImageUseCase: DeleteProductImageUseCase,
    private val addProductImageUseCase: CreateProductImageUseCase,
    private val getAllProductImagesUseCase: GetAllProductImagesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProductSellerUpdateImageState())
    val state = _state.asStateFlow()

    private val _navigateToAllProduct = MutableSharedFlow<Unit>()
    val navigateToAllProduct = _navigateToAllProduct.asSharedFlow()

    private val productId: Int = checkNotNull(savedStateHandle[SellerRoute.ProductImageUpdate.PRODUCT_ID])

    init {
        getImages()
    }

    fun confirmUpdateImage(){
        viewModelScope.launch {
            updateState {
                copy(isLoading = true)
            }
            try {
                Log.d("VM ADADEH Add",state.value.listImageAdd.toString())
                Log.d("VM ADADEH Delete",state.value.listImageDelete.toString())
                val addResult =
                if(state.value.listImageAdd.isNotEmpty()){
                    addProductImageUseCase.run(
                        id = productId,
                        images = state.value.listImageAdd
                    ).first()
                }else{
                    CommonState.Success("")
                }

                val deleteResult =
                if(state.value.listImageDelete.isNotEmpty()){
                    deleteProductImageUseCase.run(
                        ProductImageBody(state.value.listImageDelete)
                    ).first()
                }else{
                    CommonState.Success("")
                }

                if(addResult is CommonState.Success && deleteResult is CommonState.Success){
                    AppEventBus.events.emit(UiEvent.ShowSnackbar(
                        CustomSnackbarVisuals(
                            type = SnackbarType.SUCCESS,
                            message = "Success update image"
                        )
                    ))
                    _navigateToAllProduct.emit(Unit)
                }else{
                    val message = when{
                        addResult is CommonState.Error -> addResult.message
                        deleteResult is CommonState.Error -> deleteResult.message
                        else -> "Unknown error"
                    }
                    AppEventBus.events.emit(
                        UiEvent.ShowSnackbar(
                            CustomSnackbarVisuals(
                                type = SnackbarType.ERROR,
                                message = message
                            )
                        )
                    )
                }
            } finally {
                updateState {
                    copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onDeleteImageUri(index: Int){
        val newImages = state.value.listImageAdd.toMutableList()
        newImages.removeAt(index)
        updateState {
            copy(listImageAdd = newImages)
        }
    }

    fun onDeleteImageUrl(index: Int, id: Int) {
        val currentState = state.value
        val imagesState = currentState.listImage

        if (imagesState !is CommonState.Success) return

        val newImages = imagesState.data.toMutableList()

        if (index !in newImages.indices) return

        newImages.removeAt(index)

        updateState {
            copy(
                listImage = CommonState.Success(newImages),
                listImageDelete = listImageDelete + id
            )
        }
    }

    fun onImageSelected(uris: List<Uri>){
        updateState {
            copy(
                listImageAdd = listImageAdd + uris
            )
        }
    }
    private fun getImages(){
        viewModelScope.launch {
            getAllProductImagesUseCase.run(ProductImagesQuery(
                productId
            )).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(CustomSnackbarVisuals(
                                message = result.message,
                                type = SnackbarType.ERROR
                            ))
                        )
                        updateState {
                            copy(
                                isLoading = false,
                                listImage = result
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(
                                isLoading = true
                            )
                        }
                    }
                    is CommonState.Success -> {
                        updateState {
                            copy(
                                isLoading = false,
                                listImage = result
                            )
                        }
                    }
                }
            }
        }
    }
    private fun updateState(
        update: ProductSellerUpdateImageState.() -> ProductSellerUpdateImageState
    ) {
        _state.update {
            it.update()
        }
    }
}