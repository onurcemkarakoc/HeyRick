package com.onurcemkarakoc.heyrick.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient(): com.onurcemkarakoc.core.data.KtorClient {
        return com.onurcemkarakoc.core.data.KtorClient()
    }
}