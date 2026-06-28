package com.example.seapedia.presentation.buyer.profile

import com.example.seapedia.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
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
import com.example.seapedia.domain.entities.UserProfile
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.buyer.BuyerRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.buyer.profile.shimmer.ProfileBuyerShimmer
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.presentation.common.IconCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.Grey
import org.w3c.dom.Text

@Composable
fun ProfileBuyerScreen(
    modifier: Modifier = Modifier,
    rootNavController: NavController,
    buyerNavController: NavController,
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
        when(state.userProfile){
            is CommonState.Error -> {
                Text(state.userProfile.message)
            }
            is CommonState.Loading -> {
                ProfileBuyerShimmer()
            }
            is CommonState.Success -> {
                IconProfile()
                BodyProfile(
                    user = state.userProfile.data,
                    isGuest = isGuest,
                    phoneNumber = state.phoneNumber,
                    currentRole = profileBuyerViewModel.sessionState.value.role ?: UserRole.Guest,
                    onClickAddress = {
                        buyerNavController.navigate(BuyerRoute.BuyerAddress.route)
                    }
                )
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
        modifier
            .size(100.dp)
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
    user: UserProfile,
    phoneNumber: String? = null,
    currentRole: UserRole,
    isGuest: Boolean,
    onClickAddress : () -> Unit = {},
    onClickPhone : () -> Unit = {},
    onClickWallet : () -> Unit = {}
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

            if(!isGuest && currentRole == UserRole.Buyer){
                HorizontalDivider()
                ProfileButtonField(
                    onClick = onClickPhone,
                    content = {
                        Column {
                            Text(
                                text = " Phone Number : $phoneNumber",
                                style = MaterialTheme.typography.labelLarge
                            )
                            Spacer(Modifier.padding(Dimens.SpacePadding))
                            Row {
                                IconCustom(
                                    contentDescription = "Phone Icon",
                                    icon = Icons.Default.Phone,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(Modifier.padding(Dimens.SpacePadding))
                                Text(
                                    text = "$phoneNumber",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                )
                HorizontalDivider()
                ProfileButtonField(
                    onClick = onClickAddress,
                    content = {
                        Text(
                            text = "Address",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
                HorizontalDivider()
                ProfileButtonField(
                    onClick = onClickWallet,
                    content = {
                        Text(
                            text = "Wallet Transactions",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileButtonField(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimens.SpacePadding
            )
            .clickable {
                onClick()
            }
    ) {
        content()
    }
}
@Composable
fun LogoutSection(modifier: Modifier = Modifier,logout:() -> Unit) {
    ButtonCustom(modifier, enabled = true, isNotLoading = true, title = "Logout") {
        logout()
    }
}