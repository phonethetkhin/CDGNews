package com.ptk.ptk_news.network

import com.ptk.ptk_news.model.dto.response.NewsFeedResponseModel


interface ApiService {

    suspend fun getNewsFeed(query: String, pageNum: Int, language: String): NewsFeedResponseModel
    suspend fun getHeadlines(query: String, pageNum: Int, language: String): NewsFeedResponseModel


}