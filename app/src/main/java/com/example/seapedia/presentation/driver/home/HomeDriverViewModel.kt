package com.example.seapedia.presentation.driver.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.job.GetListAvailableJobUseCase
import com.example.seapedia.domain.usecases.job.GetListCurrentJobUseCase
import com.example.seapedia.domain.usecases.wallet.GetRevenueUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeDriverViewModel @Inject constructor(
    private val getListAvailableJobUseCase: GetListAvailableJobUseCase,
    private val getListCurrentJobUseCase: GetListCurrentJobUseCase,
    private val getRevenueUseCase: GetRevenueUseCase,
) : ViewModel(){
    private val _state = MutableStateFlow(HomeDriverScreenState())
    val state = _state.asStateFlow()

    init {
        onInit()
    }
    fun onRefresh(){
        onLoad(true)
    }
    fun onInit(){
        onLoad(false)
    }

    private fun onLoad(isRefreshing: Boolean = false){
        viewModelScope.launch {
            updateState {
                copy(isRefreshing = isRefreshing)
            }
            coroutineScope {
                launch {
                    getRevenueUseCase.run().collect {
                        result ->
                        if(result is CommonState.Error){
                            AppEventBus.events.emit(UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.ERROR,
                                    message = result.message
                                )
                            ))
                        }
                        updateState {
                            copy(income = result)
                        }
                    }
                }
                launch {
                    getListCurrentJobUseCase.run().collect {
                            result ->
                        if(result is CommonState.Error){
                            Log.d("Driver Home Error Current",result.message)
                            AppEventBus.events.emit(UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.ERROR,
                                    message = result.message
                                )
                            ))
                        }
                        updateState {
                            copy(listCurrentJob = result)
                        }
                    }
                }
                launch {
                    getListAvailableJobUseCase.run().collect {

                            result ->
                        if(result is CommonState.Error){
                            Log.d("Driver Home Error Available",result.message)
                            AppEventBus.events.emit(UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.ERROR,
                                    message = result.message
                                )
                            ))
                        }
                        updateState {
                            copy(listAvailableJob = result)
                        }
                    }
                }
            }
            updateState {
                copy(isRefreshing = false)
            }
        }
    }

    private fun updateState(
        updateState: HomeDriverScreenState.() -> HomeDriverScreenState
    ){
        _state.update {
            it.updateState()
        }
    }
}