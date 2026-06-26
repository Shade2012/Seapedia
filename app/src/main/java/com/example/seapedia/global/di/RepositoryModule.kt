package com.example.seapedia.global.di

import com.example.seapedia.data.repositories.AuthRepositoryImpl
import com.example.seapedia.data.repositories.ProductRepositoryImpl
import com.example.seapedia.data.repositories.RegionRepositoryImpl
import com.example.seapedia.data.repositories.ReviewRepositoryImpl
import com.example.seapedia.data.repositories.StoreRepositoryImpl
import com.example.seapedia.data.repositories.UserRepositoryImpl
import com.example.seapedia.domain.repositories.AuthRepository
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.domain.repositories.RegionRepository
import com.example.seapedia.domain.repositories.ReviewRepository
import com.example.seapedia.domain.repositories.StoreRepository
import com.example.seapedia.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(
        impl: ReviewRepositoryImpl
    ): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindStoreRepository(
        impl: StoreRepositoryImpl
    ): StoreRepository

    @Binds
    @Singleton
    abstract fun bindRegionRepository(
        impl: RegionRepositoryImpl
    ): RegionRepository
}