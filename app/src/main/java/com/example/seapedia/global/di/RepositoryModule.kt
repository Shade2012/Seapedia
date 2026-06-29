package com.example.seapedia.global.di

import com.example.seapedia.data.repositories.AddressRepositoryImpl
import com.example.seapedia.data.repositories.AuthRepositoryImpl
import com.example.seapedia.data.repositories.BuyerRepositoryImpl
import com.example.seapedia.data.repositories.CartRepositoryImpl
import com.example.seapedia.data.repositories.OrderRepositoryImpl
import com.example.seapedia.data.repositories.ProductRepositoryImpl
import com.example.seapedia.data.repositories.RegionRepositoryImpl
import com.example.seapedia.data.repositories.ReviewRepositoryImpl
import com.example.seapedia.data.repositories.StoreRepositoryImpl
import com.example.seapedia.data.repositories.UserRepositoryImpl
import com.example.seapedia.data.repositories.WalletRepositoryImpl
import com.example.seapedia.domain.repositories.AddressRepository
import com.example.seapedia.domain.repositories.AuthRepository
import com.example.seapedia.domain.repositories.BuyerRepository
import com.example.seapedia.domain.repositories.CartRepository
import com.example.seapedia.domain.repositories.OrderRepository
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.domain.repositories.RegionRepository
import com.example.seapedia.domain.repositories.ReviewRepository
import com.example.seapedia.domain.repositories.StoreRepository
import com.example.seapedia.domain.repositories.UserRepository
import com.example.seapedia.domain.repositories.WalletRepository
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

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    abstract fun bindWalletRepository(
        impl: WalletRepositoryImpl
    ): WalletRepository

    @Binds
    @Singleton
    abstract fun bindBuyerRepository(
        impl: BuyerRepositoryImpl
    ): BuyerRepository

    @Binds
    @Singleton
    abstract fun bindAddressRepository(
        impl: AddressRepositoryImpl
    ): AddressRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository
}