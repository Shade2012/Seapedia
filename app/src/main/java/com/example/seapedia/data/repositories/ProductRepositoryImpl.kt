package com.example.seapedia.data.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.seapedia.data.remote.sources.ProductRemoteDataSources
import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.data.remote.body.ProductImageBody
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.query.AllProductSellerQuery
import com.example.seapedia.data.remote.query.ProductImagesQuery
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.ProductImage
import com.example.seapedia.domain.entities.SellerProduct
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.global.utils.toMultipart
import com.example.seapedia.mapper.product.ProductImageRawMapper
import com.example.seapedia.mapper.product.ProductRawMapper
import com.example.seapedia.mapper.seller.SellerProductMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSources: ProductRemoteDataSources,
    @ApplicationContext
    private val context: Context,
) : ProductRepository {
    override suspend fun getAllProduct(queries: AllProductQuery): Flow<CommonState<List<Product>>> = flow{
        emit(CommonState.Loading())
        try {
            val response = productRemoteDataSources.getAllProduct(queries)
            val products = response.data.map {
                ProductRawMapper().mapFromResponse(it)
            }.toList()
            emit(CommonState.Success<List<Product>>(data = products))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getDetailProduct(id: Int): Flow<CommonState<Product>> = flow {
        emit(CommonState.Loading())
        try {
            val response = productRemoteDataSources.getDetailProduct(id)
            emit(CommonState.Success<Product>(data = ProductRawMapper().mapFromResponse(response.data)))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            Log.d("Error Product IMpl",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getAllSellerProduct(queries: AllProductSellerQuery): Flow<CommonState<SellerProduct>> = flow{
        emit(CommonState.Loading())
        try {
            val response = productRemoteDataSources.getAllProductBySeller(queries)
            val result = SellerProductMapper().mapFromResponse(response)
            emit(CommonState.Success<SellerProduct>(data = result))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }


    override suspend fun updateProduct(id:Int, product: CreateProductBody) : Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = productRemoteDataSources.updateProductBySeller(id,product)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            Log.d("Error Exception Repo",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun createProduct(product: CreateProductBody, images : List<Uri>): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val imagesMultipart = images.map {
                it.toMultipart(context,"product_image")
            }
            val response = productRemoteDataSources.createProductBySeller(product,imagesMultipart)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            Log.d("Error Exception Repo",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getAllProductImages(queries: ProductImagesQuery): Flow<CommonState<List<ProductImage>>> = flow{
        emit(CommonState.Loading())
        try {
            val response = productRemoteDataSources.getProductImages(queries)
            emit(CommonState.Success<List<ProductImage>>(data = response.data.map {
                ProductImageRawMapper().mapFromResponse(it)
            }))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            Log.d("Error Exception Repo",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun addProductImages(id: Int, images: List<Uri>): Flow<CommonState<String>> = flow{
        try {
            val imagesMultipart = images.map {
                it.toMultipart(context,"product_image")
            }
            val response = productRemoteDataSources.addProductImage(id,imagesMultipart)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.d("Error Exception Repo",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteProductImages(product: ProductImageBody): Flow<CommonState<String>> = flow{
        try {
            val response = productRemoteDataSources.deleteProductImages(product)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.d("Error Exception Repo",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun deleteProduct(id: Int): Flow<CommonState<String>> = flow{
        try {
            val response = productRemoteDataSources.deleteProduct(id)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            Log.d("Error Exception Repo",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

}