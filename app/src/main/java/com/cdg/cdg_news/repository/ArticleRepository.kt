package com.cdg.cdg_news.repository

import android.app.Application
import com.cdg.cdg_news.R
import com.cdg.cdg_news.db.dao.ArticleDao
import com.cdg.cdg_news.db.dao.SourceDao
import com.cdg.cdg_news.db.entity.ArticleEntity
import com.cdg.cdg_news.model.RemoteResource
import com.cdg.cdg_news.network.ApiService
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject


class ArticleRepository @Inject constructor(
    private val context: Application,
    private val apiService: ApiService,
    private val sourceDao: SourceDao,
    private val articleDao: ArticleDao,
) {
    //=======================================API functions========================================//

    suspend fun getNewsFeed(
        country: String,
        category: String,
        sources: String,
        query: String,
        pageNum: Int,
    ) = channelFlow {

        send(RemoteResource.Loading)

        try {
            val response = apiService.getNewsFeed(country, category, sources, query, pageNum)
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = context.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = context.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))

                }
            }
        }
    }

    suspend fun getArticles(
        query: String, sources: String, sortBy: String, pageNum: Int
    ) = channelFlow {

        send(RemoteResource.Loading)

        try {
            val response =
                apiService.getArticles(query, sources, sortBy, pageNum)
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = context.getString(R.string.connection_error_message)))
                }

                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = context.getString(R.string.connection_error_message)))
                }

                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }

    //=======================================DB functions=========================================//

    suspend fun getAllSourcesFromDB() = sourceDao.getAllSources()

    suspend fun insertArticlesDB(articles: List<ArticleEntity>) =
        articleDao.insertAllArticles(articles)


    suspend fun getAllNewsFeedsArticlesFromDB() = articleDao.getAllNewsFeedsArticles()

    suspend fun insertBookMarksDB(articleEntity: ArticleEntity) =
        articleDao.insertBookMark(articleEntity)


    suspend fun removeBookMarksDB(articleId: Int) =
        articleDao.removeBookMark(articleId)


    suspend fun getAllArticlesDB() = articleDao.getAllArticles()

    suspend fun getBookMarkArticleDB() = articleDao.getBookMarkArticles()

    suspend fun getArticleByIdDB(articleId: Int) = articleDao.getArticleById(articleId)

    suspend fun updateIsFavDB(isFav: Boolean, articleId: Int) =
        articleDao.updateIsFav(isFav, articleId)

    suspend fun updateCommentDB(postComment: String,articleId: Int) =
        articleDao.updateComment(postComment, articleId)


}
