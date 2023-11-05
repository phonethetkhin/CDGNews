package com.ptk.ptk_news.di

import android.content.Context
import androidx.room.Room
import com.ptk.ptk_news.db.PTKNewsDB
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
    fun providesPTKNewsDB(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PTKNewsDB::class.java,
        "ptk_news.db"
    ).build()

    @Singleton
    @Provides
    fun providesSourceDao(db: PTKNewsDB) = db.getSourceDao()


}