package com.example.seapedia.presentation.review.all


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.data.remote.query.AllReviewQuery
import com.example.seapedia.domain.usecases.review.GetAllReviewUseCase
import com.example.seapedia.domain.usecases.system.GetDayUseCase
import com.example.seapedia.global.utils.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@HiltViewModel
class ReviewAllScreenViewModel @Inject constructor(
    private val getAllReviewUseCase: GetAllReviewUseCase,
    private val getDayUseCase: GetDayUseCase
) : ViewModel(){
    private val _state = MutableStateFlow<ReviewAllState>(ReviewAllState())
    val state = _state.asStateFlow()

    init {
        getReviews()
    }

    private fun getDay() {
        viewModelScope.launch {
            getDayUseCase.run().collect { result ->
                if (result is CommonState.Success) {
                    updateState {
                        copy(daySystem = result.data)
                    }
                }
            }
        }
    }
    fun getReviews() {
        viewModelScope.launch {
            getAllReviewUseCase.run(AllReviewQuery())
                .collect { result ->
                    updateState {
                        copy(reviews = result)
                    }
                }
        }
    }
    fun refreshReviews() {
        viewModelScope.launch {
            updateState {
                copy(isRefreshing = true)
            }
            getAllReviewUseCase.run(AllReviewQuery())
                .collect { result ->
                    updateState {
                        copy(reviews = result)
                    }
                    if (result !is CommonState.Loading) {
                        updateState {
                            copy(isRefreshing = false)
                        }
                    }
                }
        }
    }
    private fun updateState(
        update: ReviewAllState.() -> ReviewAllState
    ){
        _state.update {
            it.update()
        }
    }
}