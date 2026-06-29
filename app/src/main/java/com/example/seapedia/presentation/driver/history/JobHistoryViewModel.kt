package com.example.seapedia.presentation.driver.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.job.GetListHistoryJobUseCase
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
class JobHistoryViewModel @Inject constructor(
    private val getListHistoryJobUseCase: GetListHistoryJobUseCase,
) : ViewModel(){
    private val _state = MutableStateFlow(JobHistoryScreenState())
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
            getListHistoryJobUseCase.run().collect {
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
                    copy(listJobHistory = result)
                }
            }
            updateState {
                copy(isRefreshing = false)
            }
        }
    }

    private fun updateState(
        updateState: JobHistoryScreenState.() -> JobHistoryScreenState
    ){
        _state.update {
            it.updateState()
        }
    }
}