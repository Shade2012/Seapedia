package com.example.seapedia.global.di

import com.example.seapedia.data.repositories.AuthRepositoryImpl
import com.example.seapedia.data.repositories.UserRepositoryImpl
import com.example.seapedia.domain.repositories.AuthRepository
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
}