package com.example.seapedia.presentation.driver.history

import com.example.seapedia.domain.entities.Job
import com.example.seapedia.global.utils.CommonState

data class JobHistoryScreenState(
    val listJobHistory : CommonState<List<Job>> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)