package com.example.seapedia.mapper

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ProfileResponse
import com.example.seapedia.domain.entities.UserProfileEntity
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.global.utils.UserRole

class UserProfileMapper : Mapper<BaseResponse<ProfileResponse>, UserProfileEntity> {
    override fun mapFromResponse(type: BaseResponse<ProfileResponse>): UserProfileEntity {
        return UserProfileEntity(
            id = type.data.id,
            message = type.message,
            fullName = type.data.fullName,
            email = type.data.email,
            listRoles = type.data.roles.map{ UserRole.valueOf(it.role)}
        )
    }
}