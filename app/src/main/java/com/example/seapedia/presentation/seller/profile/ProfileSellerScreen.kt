package com.example.seapedia.presentation.seller.profile

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.buyer.profile.BodyProfile
import com.example.seapedia.presentation.buyer.profile.IconProfile
import com.example.seapedia.presentation.buyer.profile.LogoutSection
import com.example.seapedia.presentation.buyer.profile.shimmer.ProfileBuyerShimmer
import com.example.seapedia.presentation.seller.widgets.SellerBody

@Composable
fun ProfileSellerScreen(
    sellerNavController: NavController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier,
    profileSellerViewModel: ProfileSellerViewModel = hiltViewModel<ProfileSellerViewModel>()) {

    val state = profileSellerViewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        profileSellerViewModel.navigateToAuth.collect {
            rootNavController.navigate(NavGraph.AUTH){
                popUpTo(0)
            }
        }
    }
    SellerBody(scrollState) {
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
                    currentRole = UserRole.Seller,
                    onClickAddress = {},
                    isGuest = false
                )
            }
        }
        if(state !is CommonState.Loading){
            LogoutSection {
                profileSellerViewModel.logout()
            }
        }
    }
}