package com.example.seapedia.domain.entities

import com.example.seapedia.global.utils.UserRole

data class UserProfile(
    val id: Int,
    val message:String,
    val fullName: String,
    val email: String,
    val listRoles: List<UserRole>
){
    companion object{
        val GUEST_PROFILE_ENTITY = UserProfile(id = 0, message = "", fullName = "Guest", email = "", listRoles = listOf(
            UserRole.Guest))
    }
}

