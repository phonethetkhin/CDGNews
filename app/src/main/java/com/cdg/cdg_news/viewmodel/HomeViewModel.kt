package com.cdg.cdg_news.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdg.cdg_news.db.entity.SourceEntity
import com.cdg.cdg_news.model.RemoteResource
import com.cdg.cdg_news.repository.HomeRepository
import com.cdg.cdg_news.ui.ui_states.ArticleUIStates
import com.cdg.cdg_news.util.datastore.MyDataStore
import com.cdg.cdg_news.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : ViewModel() {

    val _uiStates = MutableStateFlow(ArticleUIStates())
    val uiStates = _uiStates.asStateFlow()


    //=======================================api function=========================================//


    suspend fun getSources() = viewModelScope.async {

            repository.getSources().collectLatest { remoteResource ->
                when (remoteResource) {
                    is RemoteResource.Loading -> _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                    is RemoteResource.Success -> {
                        if (remoteResource.data.sources.isNotEmpty()) {
                            insertAllSources(remoteResource.data.sources)
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


    //=======================================db function=========================================//

    suspend fun insertAllSources(sources: List<SourceEntity>) = repository.insertAllSourcesDB(sources)

}
