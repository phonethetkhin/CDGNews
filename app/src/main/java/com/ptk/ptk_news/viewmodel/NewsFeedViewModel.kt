package com.ptk.ptk_news.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.db.entity.SourceEntity
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.repository.ArticleRepository
import com.ptk.ptk_news.ui.ui_states.NewsFeedUIStates
import com.ptk.ptk_news.util.datastore.MyDataStore
import com.ptk.ptk_news.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val repository: ArticleRepository,
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : ViewModel() {

    val _uiStates = MutableStateFlow(NewsFeedUIStates())
    val uiStates = _uiStates.asStateFlow()

    //=======================================states function======================================//

    fun toggleSelectedCategory(categoryId: Int) {
        _uiStates.update { it.copy(selectedCategory = categoryId) }
    }

    suspend fun toggleSelectedFilterBySource(isFilterBySource: Boolean) {
        _uiStates.update { it.copy(isFilterBySource = isFilterBySource) }

        val categoryId = getPreferredCategory() ?: 0
        val countryId = getPreferredCountry()
        val country =
            _uiStates.value.availableCountries.find { it.id == countryId }?.name
                ?: "United States"
        val sources = getPreferredSources()

        if (isFilterBySource) {
            if (_uiStates.value.availableSources.isNotEmpty()) {
                if (sources!!.isNotEmpty()) {
                    val sourcesList = sources.split(",")

                    sourcesList.forEach {
                        toggleInitialSelectedSources(it)
                    }
                }
            }
        } else {
            _uiStates.update {
                it.copy(
                    availableSources = _uiStates.value.availableSources.mapIndexed { index, details ->
                        details.copy(selected = false)

                    } as ArrayList<SourceEntity>)
            }

            toggleSelectedCategory(categoryId)
            toggleSelectedCountry(country)
        }


    }

    fun toggleSelectedCountry(selectedCountry: String) {
        _uiStates.update { it.copy(selectedCountry = selectedCountry) }
    }

    fun toggleSearchValueChange(searchValue: String) {
        _uiStates.update { it.copy(searchText = searchValue) }
    }

    fun toggleSource(source: String) {
        _uiStates.update { it.copy(source = source) }

        if (source.trim().isNotEmpty()) {
            val suggestionsList = _uiStates.value.availableSources.filter {
                it.name?.toLowerCase()?.contains(source.toLowerCase()) ?: false
            }
            if (suggestionsList.isNotEmpty()) {
                _uiStates.update {
                    it.copy(
                        sourceSuggestions = suggestionsList.map { sugg -> sugg.name!! }
                            .toCollection(ArrayList())
                    )
                }
            } else {
                _uiStates.update {
                    it.copy(
                        sourceSuggestions = arrayListOf()
                    )
                }
            }
        } else {
            _uiStates.update {
                it.copy(
                    sourceSuggestions = arrayListOf()
                )
            }
        }
    }

    fun toggleSelectedSources(selectedSource: String) {
        val selectedSourceItem = _uiStates.value.availableSources.find { it.name == selectedSource }
        _uiStates.update { uiStates ->
            uiStates.copy(
                source = "",
                sourceSuggestions = arrayListOf(),
                availableSources = _uiStates.value.availableSources.mapIndexed { index, details ->
                    if (_uiStates.value.availableSources.indexOf(_uiStates.value.availableSources.find { it.id == selectedSourceItem?.id }) == index) details.copy(
                        selected = !details.selected
                    )
                    else details
                } as ArrayList<SourceEntity>)
        }

    }

    fun toggleInitialSelectedSources(selectedSource: String) {
        val selectedSourceItem = _uiStates.value.availableSources.find { it.id == selectedSource }

        _uiStates.update { uiStates ->
            uiStates.copy(
                source = "",
                sourceSuggestions = arrayListOf(),
                availableSources = _uiStates.value.availableSources.mapIndexed { index, details ->
                    if (_uiStates.value.availableSources.indexOf(_uiStates.value.availableSources.find { it.id == selectedSourceItem?.id }) == index) details.copy(
                        selected = true
                    )
                    else details
                } as ArrayList<SourceEntity>)
        }

    }

    fun resetSelectedValue() {
        viewModelScope.launch {
            val categoryId = getPreferredCategory() ?: 0
            val countryId = getPreferredCountry() ?: 0
            val country =
                _uiStates.value.availableCountries.find { it.id == countryId }?.name
                    ?: "United States"

            toggleSelectedCategory(categoryId)
            toggleSelectedCountry(country)

            if (_uiStates.value.availableSources.isNotEmpty()) {
                val sources = getPreferredSources()
                if (sources!!.isNotEmpty()) {
                    val sourcesList = sources.split(",")

                    sourcesList.forEach {
                        toggleInitialSelectedSources(it)
                    }
                }
            }
        }
    }

    fun savePreferredSetting() {
        viewModelScope.launch {
            val categoryId = _uiStates.value.selectedCategory
            val countryId =
                _uiStates.value.availableCountries.find { it.name == _uiStates.value.selectedCountry }?.id
                    ?: 53
            val sources =
                _uiStates.value.availableSources.filter { it.selected }.map { it.id }
                    .joinToString(",")

            dataStore.savePreferredCategoryId(categoryId)
            dataStore.savePreferredCountryId(countryId)
            dataStore.savePreferredSources(sources)

        }
    }

    suspend fun getPreferredCategory() = dataStore.preferredCategoryId.first()

    suspend fun getPreferredCountry() = dataStore.preferredCountryId.first()
    suspend fun getPreferredSources() = dataStore.preferredSources.first()


    //=======================================api function=========================================//
    suspend fun getNewsFeed(pageNum: Int = 1) =
        viewModelScope.async {

            var country =
                _uiStates.value.availableCountries.find { it.name != "All Countries" && it.name == _uiStates.value.selectedCountry }?.code
                    ?: ""
            var category =
                _uiStates.value.availableCategories.find { _uiStates.value.selectedCategory != 0 && it.id == _uiStates.value.selectedCategory }?.name
                    ?: ""
            var sources = _uiStates.value.availableSources.filter { it.selected }.map { it.id }
                .joinToString(",")
            val query = _uiStates.value.searchText

            if (_uiStates.value.isFilterBySource) {
                country = ""
                category = ""
            } else {
                sources = ""
            }
            val articlesList = repository.getAllNewsFeedsArticles()
            _uiStates.update {
                it.copy(
                    showLoadingDialog = false,
                    newsFeedList = articlesList
                )
            }

              /*repository.getNewsFeed(country, category, sources, query, pageNum)
                  .collectLatest { remoteResource ->
                      when (remoteResource) {
                          is RemoteResource.Loading -> _uiStates.update {
                              it.copy(showLoadingDialog = true)
                          }

                          is RemoteResource.Success -> {
                              if (!remoteResource.data.articles.isNullOrEmpty()) {

                                  repository.insertArticles(remoteResource.data.articles.onEach { it.isHeadLine = true })

                                  _uiStates.update {
                                      it.copy(
                                          showLoadingDialog = false,
                                          newsFeedList = remoteResource.data.articles
                                      )
                                  }
                              } else {
                                  _uiStates.update {
                                      it.copy(
                                          showLoadingDialog = false,
                                          errorMessage = "No Relevant Data"
                                      )
                                  }
                              }
                          }

                          is RemoteResource.Failure -> {

                              _uiStates.update {
                                  it.copy(
                                      showLoadingDialog = false,
                                      errorMessage = "${remoteResource.errorMessage}"
                                  )
                              }
                              context.showToast(remoteResource.errorMessage.toString())
                          }
                      }
                  }*/
        }.await()

    //=======================================db function=========================================//

    suspend fun getAllSources() {
        val dbSources = repository.getAllSources()
        _uiStates.update { it.copy(availableSources = dbSources) }
        val categoryId = getPreferredCategory() ?: 0
        val countryId = getPreferredCountry() ?: 0
        val country =
            _uiStates.value.availableCountries.find { it.id == countryId }?.name ?: "United States"
        if (_uiStates.value.availableSources.isNotEmpty()) {
            val sources = getPreferredSources()

            toggleSelectedCategory(categoryId)
            toggleSelectedCountry(country)
            if (sources!!.isNotEmpty()) {
                val sourcesList = sources.split(",")

                sourcesList.forEach {
                    toggleInitialSelectedSources(it)
                }
            }
        }
    }

    suspend fun insertBookMark(articleEntity: ArticleEntity) {
        val rowId = repository.insertBookMarks(articleEntity)
        if (rowId > 0) {
            _uiStates.update { it.copy(recompose = !_uiStates.value.recompose) }
            context.showToast("Saved to Bookmarks")
        }
    }

    suspend fun removeBookMark(articleEntity: ArticleEntity) {
        repository.removeBookMarks(articleEntity.id)
        _uiStates.update { it.copy(recompose = !_uiStates.value.recompose) }
        context.showToast("Removed from Bookmarks")
    }

    suspend fun updateIsFav(articleEntity: ArticleEntity) {
        repository.updateIsFav(!articleEntity.isFav, articleEntity.id)
        _uiStates.update { it.copy(recompose = !_uiStates.value.recompose) }

    }

}
