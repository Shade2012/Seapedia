package com.example.seapedia.presentation.review.all


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.global.navigation.review.ReviewRoutes
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.home.shimmer.ReviewCardShimmer
import com.example.seapedia.presentation.common.CommonFloatingActionButton
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.RefreshCommon
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.review.all.widgets.ReviewAllSection
import com.example.seapedia.ui.theme.Dimens
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun ReviewAllScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    reviewAllScreenViewModel: ReviewAllScreenViewModel = hiltViewModel<ReviewAllScreenViewModel>()
) {
    val state = reviewAllScreenViewModel.state.collectAsStateWithLifecycle().value
    val refresh = navController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_review",false)
    LaunchedEffect(Unit) {
        refresh?.collect {
            shouldRefresh ->
            if(shouldRefresh){
                reviewAllScreenViewModel.getReviews()
                navController.currentBackStackEntry?.savedStateHandle?.set("refresh_review",false)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            CommonFloatingActionButton(
                onClick = {
                    navController.navigate(ReviewRoutes.CreateReview.route)
                },
                contentDescription = "Add Review"
            )
        }
    ) { padding ->
        RefreshCommon(
            modifier = Modifier.fillMaxSize(),
            refreshing = state.isRefreshing,
            onRefresh = reviewAllScreenViewModel::refreshReviews
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(Dimens.InnerPadding)
            ) {
                TopAppBarCustom(onBackClick = {
                    navController.popBackStack()
                }, title = "All Public Reviews")
                when (val reviewsState = state.reviews) {
                    is CommonState.Error -> {
                        FailedCommonCustom(text = reviewsState.message)
                    }

                    is CommonState.Loading<*> -> {
                        ReviewCardShimmer()
                    }

                    is CommonState.Success<List<Review>> -> {
                        ReviewAllSection(
                            reviews = reviewsState.data,
                            daySystem = state.daySystem ?: Clock.System.now()
                        )
                    }
                }
            }
        }
    }
}