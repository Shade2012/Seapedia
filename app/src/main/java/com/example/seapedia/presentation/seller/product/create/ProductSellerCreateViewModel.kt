package com.example.seapedia.presentation.seller.product.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.data.remote.body.CreateProductType
import com.example.seapedia.data.remote.body.CreateProductTypeItem
import com.example.seapedia.domain.usecases.product.CreateProductUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.Formatting
import com.example.seapedia.global.utils.NormalSupportingText
import com.example.seapedia.global.utils.NumberSupportingText
import com.example.seapedia.global.utils.RupiahSupportingText
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSellerCreateViewModel
    @Inject constructor(
        private val createProductUseCase: CreateProductUseCase
    ) : ViewModel() {
        private val _state = MutableStateFlow<ProductSellerCreateState>(ProductSellerCreateState())
        val state = _state.asStateFlow()

        private val _navigateToProductAll = MutableSharedFlow<Unit>()
        val navigateToProductAll = _navigateToProductAll.asSharedFlow()

    fun createProduct(){
        val value = state.value
        val product = CreateProductBody(
            name = value.name,
            price = value.price.toInt(),
            stock = value.stock.toInt(),
            types = value.types
        )
        viewModelScope.launch {
            createProductUseCase.run(
                images = value.images,
                product = product
            ).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        AppEventBus.events.emit(UiEvent.ShowSnackbar(
                            data = CustomSnackbarVisuals(
                                message = result.message,
                                type = SnackbarType.ERROR
                            )
                        ))
                        updateState {
                            copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(isLoading = true)
                        }
                    }
                    is CommonState.Success<*> -> {
                        AppEventBus.events.emit(UiEvent.ShowSnackbar(
                            data = CustomSnackbarVisuals(
                                message = "Successfully created the product",
                                type = SnackbarType.SUCCESS
                            )
                        ))
                        _navigateToProductAll.emit(Unit)
                        updateState {
                            copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onImageSelected(uris: List<Uri>) {
        updateState {
            copy(images = images + uris)
        }
    }

    fun onDeleteImage(index: Int){
        val newImages = state.value.images.toMutableList()
        newImages.removeAt(index)
        updateState {
            copy(images = newImages)
        }
    }

    fun onChangeName(value: String){
        updateState {
            copy(
                name = value,
                nameError = !NormalSupportingText.validate(value)
            )
        }
    }

    fun onChangePrice(value: String){
        updateState {
            copy(
                price = value,
                priceError = !RupiahSupportingText.validate(value)
            )
        }
    }

    fun onChangeStock(value: String){
        updateState {
            copy(
                stock = value,
                stockError = !NumberSupportingText.validate(value)
            )
        }
    }

    fun onAddType(){
        updateState {
            copy(
                types = types + CreateProductType(
                    name = "",
                    isMultiple = false,
                    isRequired = false,
                    items = emptyList()
                )
            )
        }
    }

    fun onDeleteType(index: Int){
        updateState {
            copy(
                types = types.filterIndexed { i,_ -> i != index }
            )
        }
    }

    fun onChangeTypeName(
        index: Int,
        value: String
    ){
        updateState {
            copy(
                types = types.mapIndexed { i, type ->
                    if(i == index){
                        type.copy(name = value)
                    }else{
                        type
                    }
                }
            )
        }
    }

    fun onChangeTypeRequired(
        index: Int,
        required: Boolean
    ){
        updateType(index){
            it.copy(isRequired = required)
        }
    }

    fun onChangeTypeMultiple(
        index: Int,
        required: Boolean
    ){
        updateType(index){
            it.copy(isMultiple = required)
        }
    }

    fun onAddItem(
        typeIndex: Int
    ) {
        updateType(typeIndex){
            type ->
            type.copy(
                items = type.items + CreateProductTypeItem(
                    name = "",
                    stock = 0,
                    price = 0
                )
            )
        }
    }

    fun onDeleteItem(
        typeIndex:Int,
        itemIndex: Int
    ){
        updateState {
            copy(types = types.mapIndexed { i, type ->
                if (i == typeIndex){
                    type.copy(
                        items = type.items.filterIndexed { j,_ ->
                            j != itemIndex
                        }
                    )
                } else{
                    type
                }
            })
        }
    }

    fun onChangeItemName(
        typeIndex:Int,
        itemIndex: Int,
        value: String
    ){
        updateItem(typeIndex,itemIndex){
            item ->
            item.copy(name = value)
        }
    }

    fun onChangeItemPrice(
        typeIndex:Int,
        itemIndex: Int,
        value: String
    ){
        updateItem(typeIndex,itemIndex){
                item ->
            item.copy(price = Formatting().formatRupiahToInt(value))
        }
    }

    fun onChangeItemStock(
        typeIndex:Int,
        itemIndex: Int,
        value: String
    ){
        updateItem(typeIndex,itemIndex){
                item ->
            item.copy(stock = value.toIntOrNull() ?: 0)
        }
    }

    private inline fun updateType(
        typeIndex: Int,
        crossinline transform: (CreateProductType) -> CreateProductType
    ) {
        updateState {
            copy(
                types = types.mapIndexed { index, type ->
                    if (index == typeIndex) transform(type) else type
                }
            )
        }
    }

    private inline fun updateItem(
        typeIndex: Int,
        itemIndex: Int,
        crossinline transform: (CreateProductTypeItem) -> CreateProductTypeItem
    ) {
        updateType(typeIndex) { type ->
            type.copy(
                items = type.items.mapIndexed { index, item ->
                    if (index == itemIndex) transform(item) else item
                }
            )
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