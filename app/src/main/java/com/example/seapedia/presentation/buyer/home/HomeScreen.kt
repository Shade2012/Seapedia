package com.example.seapedia.presentation.buyer.home

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.ui.theme.Dimens

@Composable
fun HomeBuyerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val session by homeViewModel.state.collectAsStateWithLifecycle()
    Log.d("HomeBuyerScreen","${session.isLoggedIn}")
    val isGuest = session.role == null || session.role == UserRole.Guest
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        homeViewModel.navigateToBuyer.collect {
            navController.navigate(NavGraph.AUTH){
                popUpTo(0)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(Dimens.InnerPadding)
            .padding(top = Dimens.TopPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        Text(if(isGuest) "Guest" else "Buyer")
        ButtonCustom(modifier, enabled = true, loading = true, title = "Logout") {
            homeViewModel.logout()
        }
    }
}
