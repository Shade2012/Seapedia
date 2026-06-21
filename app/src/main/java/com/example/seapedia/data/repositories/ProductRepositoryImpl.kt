package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.ProductRemoteDataSources
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.product.ProductRawMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSources: ProductRemoteDataSources
) : ProductRepository {
    override suspend fun getAllProduct(queries: AllProductQuery): Flow<CommonState<List<ProductEntity>>> = flow{
        emit(CommonState.Loading())
        try {
            val response = productRemoteDataSources.getAllProduct(queries)
            val products = response.data.map {
                ProductRawMapper().mapFromResponse(it)
            }.toList()
            emit(CommonState.Success<List<ProductEntity>>(data = products))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getDetailProduct(id: Int): Flow<CommonState<ProductEntity>> = flow {
        emit(CommonState.Loading())
        try {
            val response = productRemoteDataSources.getDetailProduct(id)
            emit(CommonState.Success<ProductEntity>(data = ProductRawMapper().mapFromResponse(response.data.first())))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

}