package com.ptk.ptk_news.repository

import android.app.Application
import com.ptk.ptk_news.R
import com.ptk.ptk_news.db.dao.SourceDao
import com.ptk.ptk_news.db.entity.SourceEntity
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.network.ApiService
import io.ktor.network.sockets.ConnectTimeoutException
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject


class HomeRepository @Inject constructor(
    private val context: Application,
    private val apiService: ApiService,
    private val sourceDao: SourceDao

) {
    //=======================================api function======================================//

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

    //=======================================api function======================================//

    suspend fun insertAllDao(sources: List<SourceEntity>) = sourceDao.insertAllSources(sources)

}
