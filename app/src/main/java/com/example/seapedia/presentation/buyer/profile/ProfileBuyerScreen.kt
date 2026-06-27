package com.example.seapedia.presentation.buyer.profile

import com.example.seapedia.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.domain.entities.UserProfileEntity
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.profile.shimmer.ProfileBuyerShimmer
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.Grey

@Composable
fun ProfileBuyerScreen(
    modifier: Modifier = Modifier,
    rootNavController: NavController,
    isGuest: Boolean,
    profileBuyerViewModel: ProfileBuyerViewModel = hiltViewModel<ProfileBuyerViewModel>()
) {
    val state = profileBuyerViewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        profileBuyerViewModel.navigateToAuth.collect {
            rootNavController.navigate(NavGraph.AUTH){
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        when(state){
            is CommonState.Error -> {
                Text(state.message)
            }
            is CommonState.Loading -> {
                ProfileBuyerShimmer()
            }
            is CommonState.Success -> {
                IconProfile()
                BodyProfile(user = state.data)
                LogoutSection {
                    profileBuyerViewModel.logout()
                }
            }
        }
    }
}

@Composable
fun IconProfile(modifier: Modifier = Modifier) {
    Box(
        modifier.size(100.dp)
            .clip(shape = CircleShape)
            .background(
            color = Grey
            ),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.default_image_person),
            contentDescription = "Default profile picture"
        )
    }
}

@Composable
fun BodyProfile(
    modifier: Modifier = Modifier,
    user: UserProfileEntity
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.InnerPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
        ) {
            Text(
                text = user.fullName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            Text(
                text = "Roles",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = user.listRoles.joinToString(", ") { it.name },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun LogoutSection(modifier: Modifier = Modifier,logout:() -> Unit) {
    ButtonCustom(modifier, enabled = true, isNotLoading = true, title = "Logout") {
        logout()
    }
}