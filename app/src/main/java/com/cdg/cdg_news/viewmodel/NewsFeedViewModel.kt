package com.cdg.cdg_news.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.viewModelScope
import com.cdg.cdg_news.model.RemoteResource
import com.cdg.cdg_news.repository.ArticleRepository
import com.cdg.cdg_news.util.datastore.MyDataStore
import com.cdg.cdg_news.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val repository: ArticleRepository,
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : BaseViewModel(repository, context, dataStore) {


    //=======================================API functions========================================//
    suspend fun getNewsFeed(pageNum: Int = 1) =
        viewModelScope.async {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
            val connected = activeNetworkInfo != null && activeNetworkInfo.isConnected()

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
                if (country.isNotEmpty() || category.isNotEmpty()) {
                    sources = ""
                }
            }

            if (connected) {
                repository.getNewsFeed(country, category, sources, query, pageNum)
                    .collectLatest { remoteResource ->
                        when (remoteResource) {
                            is RemoteResource.Loading -> _uiStates.update {
                                it.copy(showLoadingDialog = true)
                            }

                            is RemoteResource.Success -> {
                                if (!remoteResource.data.articles.isNullOrEmpty()) {

                                    repository.insertArticlesDB(remoteResource.data.articles.onEach {
                                        it.isHeadLine = true
                                    })

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
                                            errorMessage = "No relevant result"
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
            } else {
                val articlesList = repository.getAllNewsFeedsArticlesFromDB()

                if (articlesList.isEmpty()) {
                    _uiStates.update { it.copy(isShowDisconnectedDialog = true) }
                } else {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            newsFeedList = articlesList
                        )
                    }
                }
            }
        }.await()


}
