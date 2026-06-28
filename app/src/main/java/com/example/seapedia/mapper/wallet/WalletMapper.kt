package com.example.seapedia.mapper.wallet

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.wallet.WalletResponse
import com.example.seapedia.domain.entities.Wallet
import com.example.seapedia.global.utils.Mapper

class WalletMapper: Mapper<BaseResponse<WalletResponse>, Wallet> {
    override fun mapFromResponse(type: BaseResponse<WalletResponse>): Wallet {
        val value = type.data
        return Wallet(
            id = value.id,
            balance = value.balance
        )
    }
}
