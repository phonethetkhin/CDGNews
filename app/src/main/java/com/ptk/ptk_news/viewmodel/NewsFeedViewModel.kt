package com.ptk.ptk_news.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.model.dto.response.SourcesItem
import com.ptk.ptk_news.repository.NewsFeedRepository
import com.ptk.ptk_news.ui.ui_states.NewsFeedUIStates
import com.ptk.ptk_news.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val repository: NewsFeedRepository,
    private val context: Application,

    ) : ViewModel() {

    private val _uiStates = MutableStateFlow(NewsFeedUIStates())
    val uiStates = _uiStates.asStateFlow()

    //=======================================states function======================================//

    fun toggleSelectedCategory(categoryId: Int) {
        _uiStates.update { it.copy(selectedCategory = categoryId) }
    }

    fun toggleSelectedFilterBySource(isFilterBySource: Boolean) {
        _uiStates.update { it.copy(isFilterBySource = isFilterBySource) }
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
                } as ArrayList<SourcesItem>)
        }
    }


    //=======================================api function=========================================//
    suspend fun getNewsFeed(pageNum: Int = 1) =
        viewModelScope.async {
            val country =
                _uiStates.value.availableCountries.find { it.name == _uiStates.value.selectedCountry }?.code
                    ?: ""
            val category =
                _uiStates.value.availableCategories.find { it.id == _uiStates.value.selectedCategory }?.name
                    ?: ""
            val sources = _uiStates.value.availableSources.filter { it.selected }.map { it.id }
                .joinToString(",")
            val query = _uiStates.value.searchText

            repository.getNewsFeed(country, category, sources, query, pageNum)
                .collectLatest { remoteResource ->
                    when (remoteResource) {
                        is RemoteResource.Loading -> _uiStates.update {
                            it.copy(showLoadingDialog = true)
                        }

                        is RemoteResource.Success -> {
                            if (!remoteResource.data.articles.isNullOrEmpty()) {
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
                }
        }.await()

    suspend fun getArticles() = viewModelScope.async {
        repository.getArticles("Israel", 1, "en").collectLatest { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading -> _uiStates.update {
                    it.copy(showLoadingDialog = true)
                }

                is RemoteResource.Success -> {
                    if (!remoteResource.data.articles.isNullOrEmpty()) {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                articlesList = remoteResource.data.articles
                            )
                        }
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                        )
                    }
                    context.showToast(remoteResource.errorMessage.toString())
                }
            }
        }
    }.await()

    suspend fun getSources() = viewModelScope.async {
        repository.getSources().collectLatest { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading -> _uiStates.update {
                    it.copy(showLoadingDialog = true)
                }

                is RemoteResource.Success -> {
                    if (remoteResource.data.sources.isNotEmpty()) {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                availableSources = remoteResource.data.sources
                            )
                        }
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                        )
                    }
                    context.showToast(remoteResource.errorMessage.toString())
                }
            }
        }
    }.await()

}
