package com.example.seapedia.presentation.driver.profile

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.buyer.profile.BodyProfile
import com.example.seapedia.presentation.buyer.profile.IconProfile
import com.example.seapedia.presentation.buyer.profile.LogoutSection
import com.example.seapedia.presentation.buyer.profile.shimmer.ProfileBuyerShimmer
import com.example.seapedia.presentation.driver.widgets.DriverBody


@Composable
fun ProfileDriverScreen(
    rootNavController: NavHostController,
    profileDriverViewModel: ProfileDriverViewModel = hiltViewModel<ProfileDriverViewModel>()) {

    val state = profileDriverViewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    val refreshRoot = rootNavController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_profile",false)

    LaunchedEffect(Unit) {
        profileDriverViewModel.navigateToAuth.collect {
            rootNavController.navigate(NavGraph.AUTH){
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(refreshRoot) {
        refreshRoot?.collect { shouldRefresh ->
            if (shouldRefresh) {
                profileDriverViewModel.getProfile()
                rootNavController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("refresh_profile", false)
            }
        }
    }
    DriverBody (scrollState) {
        when(state){
            is CommonState.Error -> {
                Text(state.message)
            }
            is CommonState.Loading -> {
                ProfileBuyerShimmer()
            }
            is CommonState.Success -> {
                IconProfile()
                BodyProfile(
                    user = state.data,
                    currentRole = UserRole.Driver,
                    onClickAddRole = {
                        rootNavController.navigate(NavGraph.ADD_ROLE)
                    },
                    isGuest = false
                )
            }
        }
        if(state !is CommonState.Loading){
            LogoutSection {
                profileDriverViewModel.logout()
            }
        }
    }
}