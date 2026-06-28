package com.example.seapedia.presentation.buyer.profile

import com.example.seapedia.domain.entities.UserProfile
import com.example.seapedia.global.utils.CommonState

data class ProfileBuyerState (
    val userProfile: CommonState<UserProfile> = CommonState.Loading(),
    val phoneNumber: String = ""
)