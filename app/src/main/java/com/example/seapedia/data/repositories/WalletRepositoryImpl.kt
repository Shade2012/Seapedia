package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.sources.WalletRemoteDataSources
import com.example.seapedia.domain.entities.Wallet
import com.example.seapedia.domain.entities.WalletTransaction
import com.example.seapedia.domain.repositories.WalletRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.wallet.WalletMapper
import com.example.seapedia.mapper.wallet.WalletTransactionListMapper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletRemoteDataSources: WalletRemoteDataSources
): WalletRepository {
    override suspend fun getBalance(): Flow<CommonState<Wallet>> = flow{
        try {
            val response = walletRemoteDataSources.getWallet()
            val wallet = WalletMapper().mapFromResponse(response)
            emit(CommonState.Success<Wallet>(data = wallet))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        }  catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        } catch (e: CancellationException) {
            throw e
        }
    }

    override suspend fun getRevenue(): Flow<CommonState<Int>> =flow{
        try {
            val response = walletRemoteDataSources.getRevenue()
            emit(CommonState.Success<Int>(data = response.data))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        }  catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        } catch (e: CancellationException) {
            throw e
        }
    }

    override suspend fun getWalletTransactions(): Flow<CommonState<List<WalletTransaction>>> = flow{
        try {
            val response = walletRemoteDataSources.getWalletTransaction()
            val walletTsx = WalletTransactionListMapper().mapFromResponse(response)
            emit(CommonState.Success<List<WalletTransaction>>(data = walletTsx))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        }  catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        } catch (e: CancellationException) {
            throw e
        }
    }
}