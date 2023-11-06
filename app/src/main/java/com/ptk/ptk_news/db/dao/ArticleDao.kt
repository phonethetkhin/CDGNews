package com.ptk.ptk_news.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ptk.ptk_news.db.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookMark(articles: ArticleEntity): Long

    @Query("UPDATE tbl_article SET isBookMark=0 WHERE id=:articleId")
    suspend fun removeBookMark(articleId: Int)

    @Query("SELECT * FROM tbl_article WHERE id = :articleId")
    suspend fun getArticleById(articleId: Int): ArticleEntity?

    @Query("SELECT * FROM tbl_article WHERE isHeadLine = 1")
    suspend fun getAllNewsFeedsArticles(): List<ArticleEntity>

    @Query("SELECT * FROM tbl_article WHERE isHeadLine = 0")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT * FROM tbl_article WHERE isBookMark=1")
    suspend fun getBookMarkArticles(): List<ArticleEntity>

    @Query("UPDATE tbl_article SET isFav =:isFav WHERE id =:articleId")
    suspend fun updateIsFav(isFav:Boolean, articleId: Int)

    @Query("UPDATE tbl_article SET postComment=:postComment,commentTime=:commentTime WHERE id =:articleId")
    suspend fun updateComment(postComment:String, commentTime:String, articleId: Int )

}