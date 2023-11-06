package com.ptk.ptk_news.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.ptk.ptk_news.repository.ArticleRepository
import com.ptk.ptk_news.ui.ui_states.ProfileUIStates
import com.ptk.ptk_news.util.datastore.MyDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : BaseViewModel(articleRepository, context, dataStore) {

    private val _profileUIStates = MutableStateFlow(ProfileUIStates())
    val profileUIStates = _profileUIStates.asStateFlow()

    //=======================================states function======================================//

    suspend fun getThemeId() = dataStore.themeId.first()
    suspend fun getTextSizeId() = dataStore.textSizeId.first()
    fun toggleThemeId(themeId: Int) {
        viewModelScope.launch {
            _profileUIStates.update { it.copy(themeId = themeId) }
            dataStore.saveThemeId(themeId)
        }
    }

    fun toggleSelectedTextSizeString(textSizeString: String) {
        viewModelScope.launch {
            _profileUIStates.update { it.copy(selectedTextSize = textSizeString) }
            val textSizeId = when (textSizeString) {
                "S" -> 1
                "M" -> 2
                "L" -> 3
                else -> 1
            }
            dataStore.saveTextSizeId(textSizeId)

        }
    }


}
