package com.example.seapedia.presentation.buyer.home

import  com.example.seapedia.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.presentation.common.ButtonCustom
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.White

@Composable
fun HomeBuyerScreen(
    modifier: Modifier = Modifier,
    buyerNavController: NavController,
    isGuest: Boolean,
    homeBuyerViewModel: HomeBuyerViewModel = hiltViewModel<HomeBuyerViewModel>()
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(Dimens.InnerPadding)
            .padding(top = Dimens.TopPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
    ) {
        WelcomeSection()
        if(!isGuest)
            BalanceSection()
        ButtonCustom(modifier, enabled = true, loading = true, title = "Logout") {
//            homeBuyerViewModel.logout()
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
fun BalanceSection(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(Dimens.InnerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacePadding)
            ) {
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = White
                    )
                )

                Text(
                    text = "Rp 2.540.000",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = White
                    ),
                    fontWeight = FontWeight.Bold
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.money_bag),
                contentDescription = "Money Icon",
                tint = White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(48.dp)
            )
        }
    }
}
@Composable
fun ReviewSection(modifier: Modifier = Modifier) {

}