package com.cdg.cdg_news.repository

import android.app.Application
import com.cdg.cdg_news.R
import com.cdg.cdg_news.db.dao.SourceDao
import com.cdg.cdg_news.db.entity.SourceEntity
import com.cdg.cdg_news.model.RemoteResource
import com.cdg.cdg_news.network.ApiService
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject


class HomeRepository @Inject constructor(
    private val context: Application,
    private val apiService: ApiService,
    private val sourceDao: SourceDao

) {
    //=======================================API functions========================================//

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

    //=======================================DB functions=========================================//

    suspend fun insertAllSourcesDB(sources: List<SourceEntity>) =
        sourceDao.insertAllSources(sources)

}
