package com.ptk.ptk_news.util.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MyDataStore @Inject constructor(private val application: Application) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("imsDataStore")
        val THEME_ID = intPreferencesKey("themeId")

    }

    val themeId: Flow<Int?> = application.dataStore.data
        .map { preferences ->
            preferences[THEME_ID] ?: 1
        }

    suspend fun saveThemeId(themeId: Int) {
        application.dataStore.edit { preferences ->
            preferences[THEME_ID] = themeId
        }
    }


}