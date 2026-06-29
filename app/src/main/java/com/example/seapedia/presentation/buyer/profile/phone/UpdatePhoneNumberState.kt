package com.example.seapedia.presentation.buyer.profile.phone

data class UpdatePhoneNumberState(
    val phoneNumber:String = "",
    val isLoading: Boolean = true,
    val error : String? = null
)