package com.example.seapedia.presentation.driver.history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.global.navigation.driver.DriverRoute
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.presentation.common.EmptyCommonCustom
import com.example.seapedia.presentation.driver.shimmer.JobListRowSectionShimmer
import com.example.seapedia.presentation.driver.widgets.DriverBody
import com.example.seapedia.presentation.driver.widgets.DriverLazyBody
import com.example.seapedia.presentation.driver.widgets.JobCard
import com.example.seapedia.ui.theme.Dimens
import com.example.seapedia.ui.theme.ErrorColor

@Composable
fun JobHistoryScreen(
    navController: NavController,
    viewModel: JobHistoryViewModel = hiltViewModel<JobHistoryViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val refresh = navController.currentBackStackEntry?.savedStateHandle?.getStateFlow("refresh_job",false)
    LaunchedEffect(Unit) {
        refresh?.collect {
                shouldRefresh ->
            if(shouldRefresh){
                navController.currentBackStackEntry?.savedStateHandle?.set("refresh_job",false)
            }
        }
    }
    DriverLazyBody(
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::onRefresh,
    ) {
        when(val histories = state.listJobHistory){
            is CommonState.Error<*> -> {
                item {
                    Text(text = "Cannot get list jobs", style = MaterialTheme.typography.bodyMedium.copy(
                        color = ErrorColor
                    ))
                }

            }
            is CommonState.Loading<*> -> {
                item {
                    JobListRowSectionShimmer()
                }
            }
            is CommonState.Success -> {
                if(histories.data.isEmpty()){
                    item {
                        EmptyCommonCustom(text = "No History")
                    }
                }else{
                    val groupedJobs = histories.data.groupBy { it.order.status }
                    val order = listOf(
                        OrderStatus.PROCCESS,
                        OrderStatus.WAITING_DRIVER,
                        OrderStatus.ON_WAY,
                        OrderStatus.DONE,
                        OrderStatus.RETURN
                    )

                    order.forEach { status ->
                        val jobs = groupedJobs[status].orEmpty()
                        if (jobs.isNotEmpty()) {
                            item {
                                Text(
                                    text = status.displayName,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(bottom = Dimens.SpacePadding)
                                )
                            }

                            items(
                                items = jobs,
                                key = {it.id}
                            ){
                                job ->
                                JobCard(
                                    modifier = Modifier.fillMaxSize(),
                                    job = job,
                                    onClick = {
                                        navController.navigate(DriverRoute.JobDetail.createRoute(job.id))
                                    }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(Dimens.RowSpacePadding))
                            }
                        }
                    }
                }
            }
        }
    }
}