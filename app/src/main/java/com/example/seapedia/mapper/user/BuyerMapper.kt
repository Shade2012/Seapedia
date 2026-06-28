package com.example.seapedia.mapper.user

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ProfileResponse
import com.example.seapedia.domain.entities.BuyerProfile
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.mapper.UserProfileMapper

class BuyerMapper: Mapper<BaseResponse<ProfileResponse>, BuyerProfile>{
    override fun mapFromResponse(type: BaseResponse<ProfileResponse>): BuyerProfile {
        val mapperUserProfile = UserProfileMapper()
        return BuyerProfile(
            phoneNumber = type.data.buyer!!.phoneNumber,
            user = mapperUserProfile.mapFromResponse(type)
        )
    }

}