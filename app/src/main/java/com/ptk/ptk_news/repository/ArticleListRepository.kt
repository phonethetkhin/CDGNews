package com.ptk.ptk_news.repository

import android.app.Application
import com.ptk.ptk_news.R
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.network.ApiService
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class ArticleListRepository @Inject constructor(
    private val context: Application,
    private val apiService: ApiService,

    ) {
    suspend fun getNewsFeed(
        query: String,
        pageNum: Int,
        language: String
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getNewsFeed(query, pageNum, language)
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

    suspend fun getHeadlines(
        query: String,
        pageNum: Int,
        language: String
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getHeadlines(query, pageNum, language)
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

}
