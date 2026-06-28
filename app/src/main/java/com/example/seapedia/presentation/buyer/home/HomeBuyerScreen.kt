package com.example.seapedia.presentation.buyer.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.global.navigation.review.ReviewRoutes
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.home.shimmer.HomeBuyerShimmer
import com.example.seapedia.presentation.buyer.home.shimmer.ReviewCardShimmer
import com.example.seapedia.presentation.buyer.home.widgets.defaultProductSection
import com.example.seapedia.presentation.buyer.home.widgets.reviewSection
import com.example.seapedia.presentation.buyer.home.widgets.searchProductSection
import com.example.seapedia.presentation.common.BalanceSection
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.common.RefreshCommon
import com.example.seapedia.presentation.common.TextFieldCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White

@Composable
fun HomeBuyerScreen(
    modifier: Modifier = Modifier,
    buyerNavController: NavController,
    isGuest: Boolean,
    rootNavController: NavController,
    homeBuyerViewModel: HomeBuyerViewModel = hiltViewModel()
) {
    val state = homeBuyerViewModel.state.collectAsStateWithLifecycle().value
    RefreshCommon(
        modifier = Modifier.fillMaxSize(),
        refreshing = state.isRefreshing,
        onRefresh = homeBuyerViewModel::refreshHome
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.InnerPadding)
                .padding(top = Dimens.TopPadding),
            verticalArrangement = Arrangement.spacedBy(
                Dimens.SpacePadding
            ),
            horizontalArrangement = Arrangement.spacedBy(
                Dimens.RowSpacePadding
            )
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                WelcomeSection()
            }

            if (!isGuest) {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    BalanceSection(Modifier, amount = 200000)
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                SearchBar(
                    text = state.searchName,
                    enabled = true,
                    onChanged = {
                        homeBuyerViewModel.onSearchNameChange(it)
                    }
                )
            }

            when (val productsState = state.products) {

                is CommonState.Loading -> {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        HomeBuyerShimmer()
                    }
                }

                is CommonState.Error -> {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        FailedCommonCustom(
                            text = productsState.message
                        )
                    }
                }

                is CommonState.Success<List<Product>> -> {
                    if (state.searchName.isNotEmpty()) {
                        searchProductSection(
                            products = productsState.data,
                            searchName = state.searchName,
                            isGuest = isGuest,
                            buyerNavController = buyerNavController,
                            onAddToCart = {
                                homeBuyerViewModel.addToCart(it)
                            }
                        )
                    } else {
                        defaultProductSection(
                            products = productsState.data,
                            isGuest = isGuest,
                            buyerNavController = buyerNavController,
                            onAddToCart = {
                                homeBuyerViewModel.addToCart(it)
                            }
                        )
                    }

                }
            }
            when (val reviewState = state.reviews) {
                is CommonState.Error<*> -> {
                    item(
                        span = {GridItemSpan(maxLineSpan)}
                    ) {
                        FailedCommonCustom(text = "Failed to load Reveiw,${state.reviews.message}")
                    }
                }

                is CommonState.Loading -> {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        ReviewCardShimmer()
                    }
                }

                is CommonState.Success<List<Review>> -> {

                    reviewSection(
                        modifier = Modifier,
                        reviews = reviewState.data,
                        onClickAllReview = {
                            rootNavController.navigate(ReviewRoutes.AllReview.route)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun WelcomeSection(
    modifier: Modifier = Modifier,
) {
    Text("Welcome to Seapedia", style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text:String,
    enabled: Boolean,
    onChanged: (String) -> Unit
) {
    TextFieldCustom(
        modifier = modifier.clip(
            shape = RoundedCornerShape(16.dp)
        ),
        text = text,
        title = null,
        hint = "Search product",
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Text,
        enabled = enabled,
        isError = false,
        supportingText = null,
        visualTransformation = VisualTransformation.None,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = White,
                contentDescription = "Search Icon"
            )
        },
    ){
        onChanged(it)
    }
}


