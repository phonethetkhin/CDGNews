package com.ptk.ptk_news.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ptk.ptk_news.db.dao.SourceDao
import com.ptk.ptk_news.db.entity.SourceEntity


@Database(
    entities = [
        SourceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PTKNewsDB : RoomDatabase() {

    abstract fun getSourceDao(): SourceDao
}