package com.cdg.cdg_news.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cdg.cdg_news.db.dao.ArticleDao
import com.cdg.cdg_news.db.dao.SourceDao
import com.cdg.cdg_news.db.entity.ArticleEntity
import com.cdg.cdg_news.db.entity.SourceEntity


@Database(
    entities = [
        SourceEntity::class,
        ArticleEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class CDGNewsDB : RoomDatabase() {

    abstract fun getSourceDao(): SourceDao
    abstract fun getArticleDao(): ArticleDao
}