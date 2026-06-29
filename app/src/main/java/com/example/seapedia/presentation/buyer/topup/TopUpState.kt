package com.example.seapedia.presentation.buyer.topup

import com.example.seapedia.global.utils.CommonState

data class TopUpState(
    val isLoading: Boolean = false,
    val amount: String = "0",
)