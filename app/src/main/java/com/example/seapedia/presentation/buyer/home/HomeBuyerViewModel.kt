package com.example.seapedia.presentation.buyer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.query.AllReviewQuery
import com.example.seapedia.domain.usecases.product.GetAllProductUseCase
import com.example.seapedia.domain.usecases.review.GetAllReviewUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.session.SessionRepository
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeBuyerViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val getAllProductUseCase: GetAllProductUseCase,
    private val getAllReviewUseCase: GetAllReviewUseCase
) : ViewModel() {
    private val _navigateToBuyer = MutableSharedFlow<Unit>()
    private val _state = MutableStateFlow(HomeState(searchName = ""))

    val navigateToBuyer = _navigateToBuyer.asSharedFlow()

    val sessionState = sessionRepository.sessionState
    val state = _state.asStateFlow()

    init {
        observeProduct()
        getReviews()
    }

    fun onSearchNameChange(searchName: String){
        updateState {
            copy(searchName = searchName)
        }
    }

    private fun updateState(
        update: HomeState.() -> HomeState
    ) {
        _state.update {
            it.update()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeProduct(){
        viewModelScope.launch {
            state.map {
                it.searchName
            }
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest {
                    searchName ->
                    getAllProductUseCase.run(
                        AllProductQuery(name = searchName)
                    )
                }.collect {
                    result ->
                    when(result){
                        is CommonState.Error<*> -> {
                            updateState {
                                copy(
                                    products = CommonState.Error(result.message)
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
                                copy(products = CommonState.Loading())
                            }
                        }
                        is CommonState.Success<*> -> {
                            updateState {
                                copy(products=result)
                            }
                        }
                    }
                }
        }
    }

    private fun getReviews(){
        viewModelScope.launch {
            getAllReviewUseCase.run(queries = AllReviewQuery(limit = 3)).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(reviews = result)
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(reviews = CommonState.Loading())
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState {
                            copy(reviews = result)
                        }
                    }
                }
            }
        }
    }
}