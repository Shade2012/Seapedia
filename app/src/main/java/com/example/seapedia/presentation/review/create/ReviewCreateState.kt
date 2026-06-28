package com.example.seapedia.presentation.review.create

data class ReviewCreateState(
    val reviewerName:String = "",
    val comment: String = "",
    val rating : Int = 0,
    val isLoading : Boolean = false,
)
