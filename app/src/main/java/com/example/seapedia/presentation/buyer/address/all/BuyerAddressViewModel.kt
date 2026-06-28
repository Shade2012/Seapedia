package com.example.seapedia.presentation.buyer.address.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.seapedia.domain.usecases.address.DeleteAddressUseCase
import com.example.seapedia.domain.usecases.address.GetAllAddressUseCase
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
class BuyerAddressViewModel @Inject constructor(
    private val getAllAddressUseCase: GetAllAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase
) : ViewModel(){
    private val _state = MutableStateFlow(BuyerAddressState())
    val state = _state.asStateFlow()

    init {
        onInit()
    }
    fun onInit(){
        onLoad(false)
    }

    fun onRefresh(){
        onLoad(true)
    }

    fun deleteAddress(id: Int){
        viewModelScope.launch {
            val prevAddress = state.value.address
            updateState {
                copy(
                    address = CommonState.Loading()
                )
            }
            deleteAddressUseCase.run(id).collect {
                result ->
                when(result){
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
                            copy(
                                address = prevAddress
                            )
                        }
                    }
                    is CommonState.Loading<*> -> {}
                    is CommonState.Success<String> -> {
                        val removedAddress = (prevAddress as CommonState.Success).data.filter {
                            it.id == id
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.SUCCESS,
                                    message = result.data
                                )
                            )
                        )
                        updateState {
                            copy(
                                address = CommonState.Success(removedAddress)
                            )
                        }
                    }
                }
            }
            onInit()
        }
    }

    private fun onLoad(isRefreshing: Boolean){
        viewModelScope.launch {
            updateState {
                copy(isRefreshing = isRefreshing)
            }
            getAllAddressUseCase.run().collect {
                result ->
                updateState {
                    copy(
                        address = result,
                        isRefreshing = false
                    )
                }
            }
        }
    }
    private fun updateState(
        update: BuyerAddressState.() -> BuyerAddressState
    ){
        _state.update {
            it.update()
        }
    }
}