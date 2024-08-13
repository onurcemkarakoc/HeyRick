package com.onurcemkarakoc.core.data.di

import android.content.Context
import androidx.datastore.dataStore
import com.onurcemkarakoc.core.data.AppSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore
}