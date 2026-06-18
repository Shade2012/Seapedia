package com.example.base_compose.global.di

import com.example.base_compose.data.repositories.AuthRepositoryImpl
import com.example.base_compose.domain.repositories.AuthRepository
import com.example.base_compose.domain.repositories.UserRepository
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
}