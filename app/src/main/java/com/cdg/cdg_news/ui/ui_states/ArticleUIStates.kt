package com.cdg.cdg_news.ui.ui_states

import com.cdg.cdg_news.db.entity.ArticleEntity
import com.cdg.cdg_news.db.entity.SourceEntity
import com.cdg.cdg_news.model.ui_model.CategoryModel
import com.cdg.cdg_news.model.ui_model.CountryModel
import com.cdg.cdg_news.util.getCountriesList


data class ArticleUIStates(

    //NewsFeed Screen
    val showLoadingDialog: Boolean = false,
    val showCommentDialog: Boolean = false,
    val errorMessage: String = "",
    val newsFeedList: List<ArticleEntity> = listOf(),
    val isFilterBySource: Boolean = false,
    val recompose: Boolean = false,
    val selectedCategory: Int = 0,
    val availableCategories: List<CategoryModel> = listOf(
        CategoryModel(id = 0, name = "All Categories"),
        CategoryModel(id = 1, name = "business"),
        CategoryModel(id = 2, name = "entertainment"),
        CategoryModel(id = 3, name = "general"),
        CategoryModel(id = 4, name = "health"),
        CategoryModel(id = 5, name = "science"),
        CategoryModel(id = 6, name = "sports"),
        CategoryModel(id = 7, name = "technology"),
    ),

    val selectedCountry: String = "United States",
    val availableCountries: List<CountryModel> = getCountriesList(),

    val source: String = "",
    val sourceSuggestions: ArrayList<String> = arrayListOf(),
    val availableSources: List<SourceEntity> = listOf(),

    val searchText: String = "",
    val selectedArticleId: Int = 0,

    val commentText: String = "",

    val commentList: ArrayList<String> = arrayListOf(),

    val articleEntity: ArticleEntity? = null,

    val isShowDisconnectedDialog: Boolean = false,


    //Article Screen

    val sortBy: String = "",

    val articleSearchText: String = "trending",
    val selectedSortBy: Int = 1,

    val isShowFilterDialog: Boolean = false,

    val bookMarkArticles: ArrayList<ArticleEntity> = arrayListOf(),


    val articlesList: List<ArticleEntity> = listOf(),

    )