package com.example.seapedia.global.di

import com.example.seapedia.data.remote.services.AddressService
import com.example.seapedia.data.remote.services.AuthService
import com.example.seapedia.data.remote.services.BuyerService
import com.example.seapedia.data.remote.services.CartService
import com.example.seapedia.data.remote.services.OrderService
import com.example.seapedia.data.remote.services.ProductService
import com.example.seapedia.data.remote.services.RegionService
import com.example.seapedia.data.remote.services.ReviewService
import com.example.seapedia.data.remote.services.StoreService
import com.example.seapedia.data.remote.services.UserService
import com.example.seapedia.data.remote.services.WalletService
import com.example.seapedia.global.networks.NetworkConstant
import com.example.seapedia.global.utils.auth.AuthAuthenticator
import com.example.seapedia.global.utils.auth.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLogging(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit (
        okHttpClient: OkHttpClient
    ) : Retrofit {
        val json = Json{
            ignoreUnknownKeys = true
            coerceInputValues = true
            explicitNulls = false
        }
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    @Named("regional")
    fun provideRegionalRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val json = Json{
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(NetworkConstant.BASE_URL_REGION)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService{
        return retrofit.create<AuthService>(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService{
        return retrofit.create<UserService>(UserService::class.java)
    }
    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService{
        return retrofit.create<ProductService>(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewService(retrofit: Retrofit): ReviewService{
        return retrofit.create<ReviewService>(ReviewService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoreService(retrofit: Retrofit): StoreService{
        return retrofit.create<StoreService>(StoreService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderService(retrofit: Retrofit): OrderService{
        return retrofit.create<OrderService>(OrderService::class.java)
    }

    @Provides
    @Singleton
    fun provideWalletService(retrofit: Retrofit): WalletService{
        return retrofit.create<WalletService>(WalletService::class.java)
    }

    @Provides
    @Singleton
    fun provideBuyerService(retrofit: Retrofit): BuyerService{
        return retrofit.create<BuyerService>(BuyerService::class.java)
    }

    @Provides
    @Singleton
    fun provideAddressService(retrofit: Retrofit): AddressService{
        return retrofit.create<AddressService>(AddressService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartService(retrofit: Retrofit): CartService{
        return retrofit.create<CartService>(CartService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegionService(
        @Named("regional")
        retrofit: Retrofit
    ): RegionService{
        return retrofit.create<RegionService>(RegionService::class.java)
    }
}