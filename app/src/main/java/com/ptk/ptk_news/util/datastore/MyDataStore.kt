package com.ptk.ptk_news.util.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MyDataStore @Inject constructor(private val application: Application) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("imsDataStore")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("isFirstLaunch")
        val THEME_ID = intPreferencesKey("themeId")
        val TEXT_SIZE_ID = intPreferencesKey("textSizeId")
        val PREFERRED_CATEGORY_ID = intPreferencesKey("preferredCategoryId")
        val PREFERRED_COUNTRY_ID = intPreferencesKey("preferredCountryId")
        val PREFERRED_SOURCES = stringPreferencesKey("preferredSources")

    }

    val isFirstLaunch: Flow<Boolean?> = application.dataStore.data
        .map { preferences ->
            preferences[IS_FIRST_LAUNCH] ?: true
        }

    suspend fun saveIsFirstLaunch(firstLaunch: Boolean) {
        application.dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = firstLaunch
        }
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

    val textSizeId: Flow<Int?> = application.dataStore.data
        .map { preferences ->
            preferences[TEXT_SIZE_ID] ?: 2
        }

    suspend fun saveTextSizeId(textSizeId: Int) {
        application.dataStore.edit { preferences ->
            preferences[TEXT_SIZE_ID] = textSizeId
        }
    }

    val preferredCategoryId: Flow<Int?> = application.dataStore.data
        .map { preferences ->
            preferences[PREFERRED_CATEGORY_ID] ?: 0
        }

    suspend fun savePreferredCategoryId(categoryId: Int) {
        application.dataStore.edit { preferences ->
            preferences[PREFERRED_CATEGORY_ID] = categoryId
        }
    }

    val preferredCountryId: Flow<Int?> = application.dataStore.data
        .map { preferences ->
            preferences[PREFERRED_COUNTRY_ID] ?: 53
        }

    suspend fun savePreferredCountryId(countryId: Int) {
        application.dataStore.edit { preferences ->
            preferences[PREFERRED_COUNTRY_ID] = countryId
        }
    }

    val preferredSources: Flow<String?> = application.dataStore.data
        .map { preferences ->
            preferences[PREFERRED_SOURCES] ?: ""
        }

    suspend fun savePreferredSources(sources: String) {
        application.dataStore.edit { preferences ->
            preferences[PREFERRED_SOURCES] = sources
        }
    }


}