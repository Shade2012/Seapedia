package com.example.seapedia.mapper.wallet

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.wallet.WalletTransactionResponse
import com.example.seapedia.domain.entities.WalletTransaction
import com.example.seapedia.global.utils.Mapper
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


class WalletTransactionListMapper: Mapper<BaseResponse<List<WalletTransactionResponse>>, List<WalletTransaction>> {
    override fun mapFromResponse(type: BaseResponse<List<WalletTransactionResponse>>): List<WalletTransaction> {
        val mapper = WalletTransactionRawMapper()
        val value = type.data
        return value.map {
            mapper.mapFromResponse(it)
        }
    }
}

class WalletTransactionRawMapper: Mapper<WalletTransactionResponse, WalletTransaction> {
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: WalletTransactionResponse): WalletTransaction {
        return WalletTransaction(
            id = type.id,
            type = type.type,
            amount = type.amount,
            description = type.description,
            receiver = type.receiver,
            sender = type.sender,
            createdAt = Instant.parse(type.createdAt)
        )
    }
}

