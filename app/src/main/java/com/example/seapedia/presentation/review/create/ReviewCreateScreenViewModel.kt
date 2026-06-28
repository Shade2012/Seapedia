package com.example.seapedia.presentation.review.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.domain.usecases.review.CreateReviewUseCase
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.ui.AppEventBus
import com.example.seapedia.global.utils.ui.CustomSnackbarVisuals
import com.example.seapedia.global.utils.ui.SnackbarType
import com.example.seapedia.global.utils.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@HiltViewModel
class ReviewCreateScreenViewModel @Inject constructor(
    private val createReviewUseCase: CreateReviewUseCase
) : ViewModel(){
    private val _state = MutableStateFlow<ReviewCreateState>(ReviewCreateState())
    private val _navigateToReviews = MutableSharedFlow<Unit>()

    val navigateToReviews = _navigateToReviews.asSharedFlow()
    val state = _state.asStateFlow()

    @OptIn(ExperimentalTime::class)
    fun onCreateReview(){
        viewModelScope.launch(Dispatchers.IO){
            createReviewUseCase.run(
                Review(
                    reviewerName = state.value.reviewerName,
                    comment = state.value.comment,
                    rating = state.value.rating,
                )
            ).collect {
                result ->
                when(result){
                    is CommonState.Error<*> -> {
                        updateState{
                            copy(isLoading=false)
                        }
                        AppEventBus.events.emit(UiEvent.ShowSnackbar(
                            data = CustomSnackbarVisuals(
                                message = result.message,
                                type = SnackbarType.ERROR
                            )
                        ))
                    }
                    is CommonState.Loading<*> -> {
                        updateState{
                            copy(isLoading=true)
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState{
                            copy(isLoading=false)
                        }
                        AppEventBus.events.emit(UiEvent.ShowSnackbar(
                            data = CustomSnackbarVisuals(
                                message = "Successfully created the review",
                                type = SnackbarType.SUCCESS
                            )
                        ))
                        _navigateToReviews.emit(Unit)
                    }
                }
            }
        }
    }

    fun onChangeComment(comment: String){
        updateState{
            copy(comment = comment)
        }
    }

    fun onChangeReviewerName(name: String){
        updateState{
            copy(reviewerName = name)
        }
    }

    fun onChangeRating(rating: Int){
        updateState{
            copy(rating = rating)
        }
    }

    private fun updateState(
        update: ReviewCreateState.() -> ReviewCreateState
    ){
        _state.update {
            it.update()
        }
    }

}