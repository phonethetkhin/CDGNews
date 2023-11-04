package com.ptk.ptk_news.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : ViewModel() {

    private val _uiStates = MutableStateFlow(ProfileUIStates())
    val uiStates = _uiStates.asStateFlow()

    //=======================================states function======================================//

    suspend fun getThemeId() = dataStore.themeId.first()
    fun toggleThemeId(themeId: Int) {
        viewModelScope.launch {
            _uiStates.update { it.copy(themeId = themeId) }
            dataStore.saveThemeId(themeId)
        }
    }

    fun toggleSelectedTextSizeString(textSizeString: String) {
        viewModelScope.launch {
            _uiStates.update { it.copy(selectedTextSize = textSizeString) }
        }
    }


}
