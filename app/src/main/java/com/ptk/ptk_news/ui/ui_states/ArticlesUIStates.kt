package com.ptk.ptk_news.ui.ui_states

import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.db.entity.SourceEntity


data class ArticlesUIStates(

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",

    val source: String = "",
    val sortBy: String = "",
    val sourceSuggestions: ArrayList<String> = arrayListOf(),
    val availableSources: List<SourceEntity> = listOf(),

    val searchText: String = "trending",
    val selectedSortBy: Int = 1,

    val isShowFilterDialog: Boolean = false,

    val bookMarkArticles: ArrayList<ArticleEntity> = arrayListOf(),


    val articlesList: List<ArticleEntity> = listOf(),
)