package com.ptk.ptk_news.ui.ui_states

import com.ptk.ptk_news.model.dto.response.ArticlesItem
import com.ptk.ptk_news.model.dto.response.SourcesItem


data class ArticlesUIStates(

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",

    val source: String = "",
    val sourceSuggestions: ArrayList<String> = arrayListOf(),
    val availableSources: List<SourcesItem> = listOf(),

    val searchText: String = "",
    val selectedSortBy: Int = 1,


    val articlesList: List<ArticlesItem> = listOf(),
)