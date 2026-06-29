package com.example.seapedia.presentation.driver.home

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.global.navigation.NavGraph
import com.example.seapedia.global.navigation.driver.DriverRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.buyer.home.shimmer.BalanceSectionShimmer
import com.example.seapedia.presentation.common.BalanceSection
import com.example.seapedia.presentation.common.FailedCommonCustom
import com.example.seapedia.presentation.driver.shimmer.JobListRowSectionShimmer
import com.example.seapedia.presentation.driver.widgets.DriverBody
import com.example.seapedia.presentation.driver.widgets.JobListRowSection
import com.example.seapedia.ui.theme.ErrorColor


@Composable
fun HomeDriverScreen(
    rootNavController: NavController,
    navController: NavController,
    viewModel: HomeDriverViewModel = hiltViewModel<HomeDriverViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    val refresh = navController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_job",false)
    LaunchedEffect(Unit) {
        refresh?.collect {
                shouldRefresh ->
            if(shouldRefresh){
                navController.currentBackStackEntry?.savedStateHandle?.set("refresh_job",false)
            }
        }
    }
    DriverBody(
        scrollState,
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::onRefresh,
    ) {
        when(val income = state.income){
            is CommonState.Error<*> -> {
                Text(text = "Cannot get balance", style = MaterialTheme.typography.bodyMedium.copy(
                    color = ErrorColor
                ))
            }
            is CommonState.Loading<*> -> {
                BalanceSectionShimmer()
            }
            is CommonState.Success -> {
                BalanceSection(
                    title = "Income",
                    amount = income.data,
                    onClick = {
                        rootNavController.navigate(NavGraph.WALLET_TRANSACTIONS)
                    }
                )
            }
        }
        when(val currentJobs = state.listCurrentJob){
            is CommonState.Error<*> -> {
                FailedCommonCustom(text = "Failed to get current jobs")
            }
            is CommonState.Loading<*> -> {
                JobListRowSectionShimmer()
            }
            is CommonState.Success -> {
                JobListRowSection(
                    title = "Current Jobs",
                    onClick = {
                        navController.navigate(DriverRoute.JobDetail.createRoute(it.id))
                    },
                    jobs = currentJobs.data
                )
            }
        }
        when(val available = state.listAvailableJob){
            is CommonState.Error<*> -> {
                Text(text = "Cannot get available jobs", style = MaterialTheme.typography.bodyMedium.copy(
                    color = ErrorColor
                ))
            }
            is CommonState.Loading<*> -> {
                JobListRowSectionShimmer()
            }
            is CommonState.Success -> {
                JobListRowSection(
                    title = "Available Job",
                    jobs = available.data,
                    onClick = {
                        navController.navigate(DriverRoute.JobDetail.createRoute(it.id))
                    }
                )
            }
        }
    }
}


