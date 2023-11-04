package com.ptk.ptk_news.repository

import android.app.Application
import com.ptk.ptk_news.R
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.network.ApiService
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject


class NewsFeedRepository @Inject constructor(
    private val context: Application,
    private val apiService: ApiService,

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
        query: String,
        pageNum: Int,
        language: String
    ) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getArticles(query, pageNum, language)
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

    suspend fun getSources() = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getAllSources()
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
