package com.example.seapedia.presentation.seller.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.seapedia.presentation.common.RefreshCommon
import com.example.seapedia.ui.theme.Dimens
import kotlin.Boolean


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerLazyBody(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    content: LazyListScope.() -> Unit
) {

    val listState = rememberLazyListState()

    RefreshCommon (
        modifier = modifier,
        refreshing = isRefreshing,
        onRefresh=onRefresh
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.InnerPadding),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
            contentPadding = PaddingValues(
                top = Dimens.TopPadding,
                bottom = Dimens.InnerPadding
            ),
            content = content
        )

    }

}