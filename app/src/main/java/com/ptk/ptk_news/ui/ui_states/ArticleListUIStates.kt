package com.ptk.ptk_news.ui.ui_states

import com.ptk.ptk_news.model.dto.response.ArticlesItem


data class ArticleListUIStates(

    val showLoadingDialog: Boolean = false,
    val newsFeedList: List<ArticlesItem> = listOf(),
)