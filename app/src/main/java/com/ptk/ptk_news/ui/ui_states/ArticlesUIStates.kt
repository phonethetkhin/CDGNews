package com.ptk.ptk_news.ui.ui_states

import com.ptk.ptk_news.db.entity.SourceEntity
import com.ptk.ptk_news.model.dto.response.ArticlesItem


data class ArticlesUIStates(

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",

    val source: String = "",
    val sortBy: String = "",
    val sourceSuggestions: ArrayList<String> = arrayListOf(),
    val availableSources: List<SourceEntity> = listOf(),

    val searchText: String = "",
    val selectedSortBy: Int = 1,

    val isShowFilterDialog: Boolean = false,


    val articlesList: List<ArticlesItem> = listOf(),
)