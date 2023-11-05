package com.ptk.ptk_news.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ptk.ptk_news.db.dao.ArticleDao
import com.ptk.ptk_news.db.dao.SourceDao
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.db.entity.SourceEntity


@Database(
    entities = [
        SourceEntity::class,
        ArticleEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class PTKNewsDB : RoomDatabase() {

    abstract fun getSourceDao(): SourceDao
    abstract fun getArticleDao(): ArticleDao
}