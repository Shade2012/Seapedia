package com.example.seapedia.global.navigation.review

sealed class ReviewRoutes(val route: String,val name: String){
    object AllReview : ReviewRoutes("all_review","all_review_screen")
    object CreateReview : ReviewRoutes("create_review", name = "create_review_screen")
}