package com.ptk.ptk_news.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.model.dto.response.SourcesItem
import com.ptk.ptk_news.repository.NewsFeedRepository
import com.ptk.ptk_news.ui.ui_states.ArticlesUIStates
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
class ArticlesViewModel @Inject constructor(
    private val repository: NewsFeedRepository,
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : ViewModel() {

    val _uiStates = MutableStateFlow(ArticlesUIStates())
    val uiStates = _uiStates.asStateFlow()

    //=======================================states function======================================//

    fun toggleSearchValueChange(searchValue: String) {
        _uiStates.update { it.copy(searchText = searchValue) }
    }

    fun toggleSortBy(sortBy: Int) {
        _uiStates.update { it.copy(selectedSortBy = sortBy) }
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

    suspend fun getArticles() = viewModelScope.async {
        val query = _uiStates.value.searchText
        val sources = _uiStates.value.availableSources.filter { it.selected }.map { it.id }
            .joinToString(",")

        repository.getArticles(query, sources, "popularity", 1).collectLatest { remoteResource ->
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

}
