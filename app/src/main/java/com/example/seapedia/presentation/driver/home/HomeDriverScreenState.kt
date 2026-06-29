package com.example.seapedia.presentation.driver.home

import com.example.seapedia.domain.entities.Job
import com.example.seapedia.global.utils.CommonState

data class HomeDriverScreenState(
    val income: CommonState<Int> = CommonState.Loading(),
    val listAvailableJob: CommonState<List<Job>> = CommonState.Loading(),
    val listCurrentJob: CommonState<List<Job>> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)