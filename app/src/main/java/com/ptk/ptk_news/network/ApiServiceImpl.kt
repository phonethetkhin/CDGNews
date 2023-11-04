package com.ptk.ptk_news.network


import com.ptk.ptk_news.model.dto.response.NewsFeedResponseModel
import com.ptk.ptk_news.util.Constants
import com.ptk.ptk_news.util.Constants.BASE_URL
import com.ptk.ptk_news.util.datastore.IMSDataStore
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val dataStore: IMSDataStore,
) : ApiService {


    override suspend fun getNewsFeed(
        query: String,
        pageNum: Int,
        language: String
    ): NewsFeedResponseModel = client.get {
        url(BASE_URL + APIRoutes.newsFeed)
        contentType(ContentType.Application.Json)
        parameter("apiKey", Constants.API_KEY)
        parameter("q", query)
        parameter("pageNum", pageNum)
        parameter("language", language)

    }

    override suspend fun getHeadlines(
        query: String,
        pageNum: Int,
        language: String
    ): NewsFeedResponseModel = client.get {
        url(BASE_URL + APIRoutes.newsFeed)
        contentType(ContentType.Application.Json)
        parameter("apiKey", Constants.API_KEY)
        parameter("q", query)
        parameter("pageNum", pageNum)
        parameter("language", language)
    }


}