package com.cdg.cdg_news.di

import com.cdg.cdg_news.network.ApiService
import com.cdg.cdg_news.network.ApiServiceImpl
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

