package com.ptk.ptk_news.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.viewModelScope
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.repository.ArticleRepository
import com.ptk.ptk_news.util.datastore.MyDataStore
import com.ptk.ptk_news.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val repository: ArticleRepository,
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : BaseViewModel(repository, context, dataStore) {

//=======================================States function======================================//

    fun toggleSortBy(sortBy: Int) {
        _uiStates.update { it.copy(selectedSortBy = sortBy) }

    }

    fun toggleIsShowFilterSourceDialog(isShowFilterSourceDialog: Boolean) {
        _uiStates.update { it.copy(isShowFilterDialog = isShowFilterSourceDialog) }
    }


    suspend fun getBookMarkArticles() {
        val bookMarkArticles = repository.getBookMarkArticleDB()
        _uiStates.update { it.copy(bookMarkArticles = bookMarkArticles.toCollection(ArrayList())) }
    }

    fun savePreferredSettingForArticle() {
        viewModelScope.launch {
            val sources =
                _uiStates.value.availableSources.filter { it.selected }.map { it.id }
                    .joinToString(",")

            dataStore.savePreferredSources(sources)
        }
    }

    fun resetSelectedValueForArticle() {
        viewModelScope.launch {

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

    //=======================================api function=========================================//

    suspend fun getArticles() = viewModelScope.async {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
        val connected = activeNetworkInfo != null && activeNetworkInfo.isConnected()

        val query = _uiStates.value.articleSearchText
        val sources = _uiStates.value.availableSources.filter { it.selected }.map { it.id }
            .joinToString(",")

        val sortBy = when (_uiStates.value.selectedSortBy) {
            1 -> "relevancy"
            2 -> "popularity"
            3 -> "publishedAt"
            else -> "publishedAt"
        }


        if (connected) {
            repository.getArticles(query, sources, sortBy, 1)
                .collectLatest { remoteResource ->
                    when (remoteResource) {
                        is RemoteResource.Loading -> _uiStates.update {
                            it.copy(showLoadingDialog = true)
                        }

                        is RemoteResource.Success -> {
                            if (!remoteResource.data.articles.isNullOrEmpty()) {

                                repository.insertArticlesDB(remoteResource.data.articles.onEach {
                                    it.isHeadLine = false
                                })

                                _uiStates.update {
                                    it.copy(
                                        showLoadingDialog = false,
                                        articlesList = remoteResource.data.articles
                                    )
                                }
                            } else {
                                _uiStates.update {
                                    it.copy(
                                        showLoadingDialog = false,
                                        errorMessage = "No relevant result",
                                        articlesList = arrayListOf()
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
        } else {
            val articlesList = repository.getAllArticlesDB()

            if (articlesList.isEmpty()) {
                _uiStates.update { it.copy(isShowDisconnectedDialog = true) }
            } else {
                _uiStates.update {
                    it.copy(
                        showLoadingDialog = false,
                        articlesList = articlesList
                    )
                }
            }
        }
    }.await()


}
