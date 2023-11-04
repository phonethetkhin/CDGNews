package com.ptk.ptk_news.network


import com.ptk.ptk_news.model.dto.response.NewsFeedResponseModel
import com.ptk.ptk_news.model.dto.response.SourceResponseModel
import com.ptk.ptk_news.util.Constants
import com.ptk.ptk_news.util.Constants.BASE_URL
import com.ptk.ptk_news.util.datastore.MyDataStore
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
    private val dataStore: MyDataStore,
) : ApiService {


    override suspend fun getNewsFeed(
        country: String,
        category: String,
        sources: String,
        query: String,
        pageNum: Int,
    ): NewsFeedResponseModel = client.get {
        url(BASE_URL + APIRoutes.newsFeed)
        contentType(ContentType.Application.Json)
        parameter("apiKey", Constants.API_KEY)
        if (country.isNotEmpty()) {
            parameter("country", country)
        }
        if (category.isNotEmpty()) {
            parameter("category", category)
        }
        if (sources.trim().isNotEmpty()) {
            parameter("sources", sources)
        }
        if (query.isNotEmpty()) {
            parameter("q", query)
        }
        parameter("page", pageNum)

    }

    override suspend fun getArticles(
        query: String,
        pageNum: Int,
        language: String
    ): NewsFeedResponseModel = client.get {
        url(BASE_URL + APIRoutes.articles)
        contentType(ContentType.Application.Json)
        parameter("apiKey", Constants.API_KEY)
        parameter("q", query)
        parameter("pageNum", pageNum)
        parameter("language", language)
    }

    override suspend fun getAllSources(): SourceResponseModel = client.get {
        url(BASE_URL + APIRoutes.sources)
        contentType(ContentType.Application.Json)
        parameter("apiKey", Constants.API_KEY)

    }


}