package com.ptk.ptk_news.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.ptk_news.model.RemoteResource
import com.ptk.ptk_news.repository.ArticleListRepository
import com.ptk.ptk_news.ui.ui_states.ArticleListUIStates
import com.ptk.ptk_news.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val repository: ArticleListRepository,
    private val context: Application,

    ) : ViewModel() {

    private val _uiStates = MutableStateFlow(ArticleListUIStates())
    val uiStates = _uiStates.asStateFlow()

    suspend fun getNewsFeed() = viewModelScope.async {
        repository.getNewsFeed("Cat", 1, "en").collectLatest { remoteResource ->
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
