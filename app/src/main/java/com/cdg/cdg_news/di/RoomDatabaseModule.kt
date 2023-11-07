package com.cdg.cdg_news.di

import android.content.Context
import androidx.room.Room
import com.cdg.cdg_news.db.CDGNewsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun providesCDGNewsDB(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        CDGNewsDB::class.java,
        "cdg_news.db"
    ).build()

    @Singleton
    @Provides
    fun providesSourceDao(db: CDGNewsDB) = db.getSourceDao()

    @Singleton
    @Provides
    fun providesArticleDao(db: CDGNewsDB) = db.getArticleDao()


}