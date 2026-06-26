package com.example.seapedia.presentation.seller.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.profile.BodyProfile
import com.example.seapedia.presentation.buyer.profile.IconProfile
import com.example.seapedia.presentation.buyer.profile.LogoutSection
import com.example.seapedia.presentation.buyer.profile.shimmer.ProfileBuyerShimmer
import com.example.seapedia.presentation.seller.widgets.SellerBody
import com.example.seapedia.ui.theme.Dimens

@Composable
fun HomeSellerScreen(
    navController: NavController
) {
//    val state = profileBuyerViewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
//    LaunchedEffect(Unit) {
//        profileBuyerViewModel.navigateToBuyer.collect {
//            rootNavController.navigate(NavGraph.AUTH){
//                popUpTo(0)
//            }
//        }
//    }
    SellerBody(scrollState){
        Text("Home")
        Text("Anjay")
    }
}