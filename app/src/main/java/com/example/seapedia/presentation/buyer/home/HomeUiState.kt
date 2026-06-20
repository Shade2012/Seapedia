package com.example.seapedia.presentation.buyer.home

import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.CommonState

data class HomeUiState(
    val searchName: String = "",
    val products: CommonState<List<ProductEntity>> = CommonState.Loading()
)