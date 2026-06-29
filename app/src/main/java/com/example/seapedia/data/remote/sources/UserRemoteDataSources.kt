package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.body.AddRoleBody
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ProfileResponse
import com.example.seapedia.data.remote.services.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSources @Inject constructor(
    private val userService: UserService
) {
    suspend fun getProfile(): BaseResponse<ProfileResponse> = userService.getProfile()
    suspend fun addRole(body: AddRoleBody): BaseMessage = userService.addRole(body)
}