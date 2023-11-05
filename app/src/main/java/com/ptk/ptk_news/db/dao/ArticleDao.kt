package com.ptk.ptk_news.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.db.entity.SourceEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM tbl_article WHERE isHeadLine = 1")
    suspend fun getAllNewsFeedsArticles(): List<ArticleEntity>

    @Query("SELECT * FROM tbl_article WHERE isHeadLine = 0")
    suspend fun getAllArticles(): List<ArticleEntity>

}