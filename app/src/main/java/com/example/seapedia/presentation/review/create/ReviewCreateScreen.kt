package com.example.seapedia.presentation.review.create

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.presentation.common.TopAppBarCustom
import com.example.seapedia.presentation.review.widgets.RatingBar
import com.example.seapedia.ui.theme.Dimens

@Composable
fun ReviewCreateScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    reviewCreateScreenViewModel: ReviewCreateScreenViewModel = hiltViewModel<ReviewCreateScreenViewModel>()
) {
    val state by reviewCreateScreenViewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        reviewCreateScreenViewModel.navigateToReviews.collect {
            navController.previousBackStackEntry?.savedStateHandle?.set("refresh_review",true)
            navController.popBackStack()
        }
    }
    Scaffold{ padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimens.InnerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarCustom(onBackClick = {
                navController.popBackStack()
            },title = "Create Public Review")
            RatingBar(
                size = 32.dp,
                readOnly = state.loading, ratingState = state.rating){
                reviewCreateScreenViewModel.onChangeRating(it)
            }
            TextFieldCustom(
                enabled = !state.loading,
                title = "Reviewer Name",
                hint = "Input your name",
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                text = state.reviewerName,

            ) {
                reviewCreateScreenViewModel.onChangeReviewerName(it)
            }
            TextFieldCustom(
                title = "Comment",
                hint = "Input your comment",
                minLines = 5,
                enabled = !state.loading,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                text = state.comment,
            ) {
                reviewCreateScreenViewModel.onChangeComment(it)
            }
            ButtonCustom(
                title = "Create",
                enabled = state.rating > 0 &&
                        state.reviewerName.isNotEmpty() &&
                        state.comment.isNotEmpty() &&
                        !state.loading,
                loading = !state.loading,
            ) {
                reviewCreateScreenViewModel.onCreateReview()
            }
        }
    }
}