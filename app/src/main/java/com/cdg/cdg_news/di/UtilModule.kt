package com.cdg.cdg_news.di

import android.app.Application
import com.cdg.cdg_news.util.datastore.MyDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Singleton
    @Provides
    fun providesDataStore(application: Application) =
        MyDataStore(application)
}