package com.ptk.ptk_news.di

import com.ptk.ptk_news.network.ApiService
import com.ptk.ptk_news.network.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideApiService(apiService: ApiServiceImpl): ApiService = apiService

}

