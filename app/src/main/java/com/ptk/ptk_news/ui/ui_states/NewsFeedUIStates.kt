package com.ptk.ptk_news.ui.ui_states

import com.ptk.ptk_news.model.dto.response.ArticlesItem
import com.ptk.ptk_news.model.dto.response.SourcesItem
import com.ptk.ptk_news.model.ui_model.CategoryModel
import com.ptk.ptk_news.model.ui_model.CountryModel
import com.ptk.ptk_news.util.getCountriesList


data class NewsFeedUIStates(

    val showLoadingDialog: Boolean = false,
    val errorMessage: String = "",
    val newsFeedList: List<ArticlesItem> = listOf(),
    val isFilterBySource: Boolean = false,

    val selectedCategory: Int = 0,
    val availableCategories: List<CategoryModel> = listOf(
        CategoryModel(id = 1, name = "business"),
        CategoryModel(id = 2, name = "entertainment"),
        CategoryModel(id = 3, name = "general"),
        CategoryModel(id = 4, name = "health"),
        CategoryModel(id = 5, name = "science"),
        CategoryModel(id = 6, name = "sports"),
        CategoryModel(id = 7, name = "technology"),
    ),

    val selectedCountry: String = "us",
    val availableCountries: List<CountryModel> = getCountriesList(),

    val source: String = "",
    val sourceSuggestions: ArrayList<String> = arrayListOf(),
    val availableSources: List<SourcesItem> = listOf(),

    val searchText: String = "",


    val articlesList: List<ArticlesItem> = listOf(),
)