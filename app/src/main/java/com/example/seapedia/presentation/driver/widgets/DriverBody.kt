package com.example.seapedia.presentation.driver.widgets

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import clearFocusOnTap
import com.example.seapedia.presentation.common.RefreshCommon
import com.example.seapedia.ui.theme.Dimens

@Composable
fun DriverBody(
    scrollState: ScrollState,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    enableRefresh: Boolean = true,
    content : @Composable ColumnScope.() -> Unit
) {
    val body: @Composable BoxScope.() -> Unit = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(Dimens.InnerPadding)
                .padding(top = Dimens.TopPadding)
                .clearFocusOnTap(LocalFocusManager.current),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding),
            content = content
        )
    }
    if(enableRefresh){
        RefreshCommon(
            modifier = Modifier.fillMaxSize(),
            refreshing = isRefreshing,
            onRefresh = onRefresh,
            content = body
        )
    }else{
        Box(
            modifier = Modifier.fillMaxSize(),
            content = body
        )
    }
}