package com.ptk.ptk_news.network

import com.ptk.ptk_news.model.dto.response.NewsFeedResponseModel
import com.ptk.ptk_news.model.dto.response.SourceResponseModel


interface ApiService {

    suspend fun getNewsFeed(country:String, category:String, sources:String, query: String, pageNum: Int): NewsFeedResponseModel
    suspend fun getArticles(query: String, sources:String, sortBy:String, pageNum: Int): NewsFeedResponseModel
    suspend fun getAllSources(): SourceResponseModel


}