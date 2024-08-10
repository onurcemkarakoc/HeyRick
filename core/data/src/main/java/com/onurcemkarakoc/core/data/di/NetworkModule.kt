package com.onurcemkarakoc.core.data.di

import com.onurcemkarakoc.core.data.KtorClient
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
    fun provideKtorClient(): KtorClient {
        return KtorClient()
    }
}