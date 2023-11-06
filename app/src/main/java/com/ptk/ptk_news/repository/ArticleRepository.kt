package com.ptk.ptk_news.repository

import android.app.Application
import com.ptk.ptk_news.R
import com.ptk.ptk_news.db.dao.ArticleDao
import com.ptk.ptk_news.db.dao.SourceDao
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.network.ApiService
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
    suspend fun getNewsFeed(
        country: String,
        category: String,
        sources: String,
        query: String,
        pageNum: Int,
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getNewsFeed(country, category, sources, query, pageNum)
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

                    val errorMessage = "Something went wrong:"
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

    //=======================================db function======================================//

    suspend fun getAllSources() = sourceDao.getAllSources()

    suspend fun insertArticles(articles: List<ArticleEntity>) =
        articleDao.insertAllArticles(articles)

    suspend fun getAllNewsFeedsArticles() = articleDao.getAllNewsFeedsArticles()
    suspend fun insertBookMarks(articleEntity: ArticleEntity) =
        articleDao.insertBookMark(articleEntity)

    suspend fun removeBookMarks(articleId:Int) =
        articleDao.removeBookMark(articleId)

    suspend fun getAllArticles() = articleDao.getAllArticles()
    suspend fun getBookMarkArticle() = articleDao.getBookMarkArticles()
    suspend fun updateIsFav(isFav:Boolean, articleId: Int) = articleDao.updateIsFav(isFav, articleId)


}
