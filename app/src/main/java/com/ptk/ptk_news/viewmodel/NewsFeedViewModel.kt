package com.ptk.ptk_news.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.model.dto.response.SourcesItem
import com.ptk.ptk_news.repository.NewsFeedRepository
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
    private val repository: NewsFeedRepository,
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : ViewModel() {

    val _uiStates = MutableStateFlow(NewsFeedUIStates())
    val uiStates = _uiStates.asStateFlow()

    //=======================================states function======================================//

    fun toggleSelectedCategory(categoryId: Int) {
        _uiStates.update { it.copy(selectedCategory = categoryId) }
    }

    fun toggleSelectedFilterBySource(isFilterBySource: Boolean) {
        _uiStates.update { it.copy(isFilterBySource = isFilterBySource) }
        if (isFilterBySource) {
            _uiStates.update { it.copy(selectedCategory = 0, selectedCountry = "") }
        } else {
            _uiStates.update {
                it.copy(availableSources = _uiStates.value.availableSources.mapIndexed { index, details ->
                    details.copy(selected = false)
                } as ArrayList<SourcesItem>)
            }

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
                } as ArrayList<SourcesItem>)
        }
    }

    fun resetSelectedValue() {
        viewModelScope.launch {
            val categoryId = getPreferredCategory() ?: 0
            val countryId = getPreferredCountry()
            val country =
                _uiStates.value.availableCountries.find { it.id == countryId }?.name
                    ?: "United States"

            toggleSelectedCategory(categoryId)
            toggleSelectedCountry(country)
        }
    }

    fun savePreferredSetting() {
        viewModelScope.launch {
            val categoryId = _uiStates.value.selectedCategory
            val countryId =
                _uiStates.value.availableCountries.find { it.name == _uiStates.value.selectedCountry }?.id
                    ?: 53

            Log.e("tesjkasdfId3", categoryId.toString())
            Log.e("tesjkasdfId4", countryId.toString())
            dataStore.savePreferredCategoryId(categoryId)
            dataStore.savePreferredCountryId(countryId)
        }
    }

    suspend fun getPreferredCategory() = dataStore.preferredCategoryId.first()

    suspend fun getPreferredCountry() = dataStore.preferredCountryId.first()


    //=======================================api function=========================================//
    suspend fun getNewsFeed(pageNum: Int = 1) =
        viewModelScope.async {
            var selectedCountry: String = ""
            if (_uiStates.value.selectedCountry != "All Countries") {
                selectedCountry = _uiStates.value.selectedCountry
            }
            val country =
                _uiStates.value.availableCountries.find { it.name == selectedCountry }?.code
                    ?: ""
            val category =
                _uiStates.value.availableCategories.find { it.id == _uiStates.value.selectedCategory }?.name
                    ?: ""
            val sources = _uiStates.value.availableSources.filter { it.selected }.map { it.id }
                .joinToString(",")
            val query = _uiStates.value.searchText


            Log.e("requestMessage1", country)
            Log.e("requestMessage2", category)
            Log.e("requestMessage3", sources)
            Log.e("requestMessage4", query)

            /* repository.getNewsFeed(country, category, sources, query, pageNum)
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
                 }*/
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
