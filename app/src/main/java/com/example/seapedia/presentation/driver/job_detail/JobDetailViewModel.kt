package com.example.seapedia.presentation.driver.job_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.job.ConfirmJobUseCase
import com.example.seapedia.domain.usecases.job.GetDetailJobUseCase
import com.example.seapedia.domain.usecases.job.TakeJobUseCase
import com.example.seapedia.domain.usecases.system.GetDayUseCase
import com.example.seapedia.global.navigation.driver.DriverRoute
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@HiltViewModel
class JobDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailJobUseCase: GetDetailJobUseCase,
    private val takeJobUseCase: TakeJobUseCase,
    private val confirmJobUseCase: ConfirmJobUseCase,
    private val getDayUseCase: GetDayUseCase,
): ViewModel(){
    private val _state = MutableStateFlow<JobDetailState>(JobDetailState())
    val state = _state.asStateFlow()

    private val _triggerRefresh = MutableSharedFlow<Unit>()
    val triggerRefresh =_triggerRefresh.asSharedFlow()
    private val jobId: Int = checkNotNull(savedStateHandle[DriverRoute.JobDetail.JOB_ID])

    init {
        onInit()
    }
    fun onInit(){
        onLoadData(isRefreshing = false)
    }
    fun onRefresh(){
        onLoadData(isRefreshing = true)
    }

    fun onTakeJob(){
        viewModelScope.launch {
            updateState {
                copy(isLoading = true)
            }
            takeJobUseCase.run(jobId).collect {
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
                            copy(isLoading = false)
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(isLoading = true)
                        }
                    }
                    is CommonState.Success -> {
                        updateState {
                            copy(isLoading = false)
                        }
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.SUCCESS,
                                    message = result.data
                                )
                            )
                        )
                        _triggerRefresh.emit(Unit)
                        onInit()
                    }
                }
            }
        }
    }
    fun onConfirmJob(){
        viewModelScope.launch {
            updateState {
                copy(isLoading = true)
            }
            confirmJobUseCase.run(jobId).collect {
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
                            copy(isLoading = false)
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(isLoading = true)
                        }
                    }
                    is CommonState.Success -> {
                        AppEventBus.events.emit(
                            UiEvent.ShowSnackbar(
                                CustomSnackbarVisuals(
                                    type = SnackbarType.SUCCESS,
                                    message = result.data
                                )
                            )
                        )
                        _triggerRefresh.emit(Unit)
                        onInit()
                    }
                }
            }
        }
    }
    private fun onLoadData(isRefreshing: Boolean = false){
        viewModelScope.launch {
            getDayUseCase.run().collect {
                result ->
                if(result is CommonState.Success){
                    updateState {
                        copy(daySystem = result.data)
                    }
                }
            }
            updateState {
                copy(isRefreshing = isRefreshing)
            }
            getDetailJobUseCase.run(jobId).collect {
                result ->
                updateState {
                    copy(
                        job = result
                    )
                }
            }
            updateState {
                copy(
                    isRefreshing = false
                )
            }
        }
    }
    private fun updateState(
        updateState: JobDetailState.() -> JobDetailState
    ){
        _state.update {
            it.updateState()
        }
    }
}