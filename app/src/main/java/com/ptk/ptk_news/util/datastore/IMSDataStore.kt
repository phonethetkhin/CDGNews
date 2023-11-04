package com.ptk.ptk_news.util.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IMSDataStore @Inject constructor(private val application: Application) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("imsDataStore")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("isFirstLaunch")
        val HAS_PROCESSED_KEY = booleanPreferencesKey("hasProcessed")
        val TOKEN = stringPreferencesKey("token")
        val USER_ID = stringPreferencesKey("userId")
        val USER_NAME = stringPreferencesKey("userName")
    }

    val isFirstLaunch: Flow<Boolean?> = application.dataStore.data
        .map { preferences ->
            preferences[IS_FIRST_LAUNCH] ?: true
        }

    suspend fun saveIsFirstLaunch(isFirstLaunch: Boolean) {
        application.dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    val hasProcessed: Flow<Boolean?> = application.dataStore.data
        .map { preferences ->
            preferences[HAS_PROCESSED_KEY] ?: false
        }

    suspend fun saveHasProcessed(hasProcessed: Boolean) {
        application.dataStore.edit { preferences ->
            preferences[HAS_PROCESSED_KEY] = hasProcessed
        }
    }

    val userId: Flow<String> = application.dataStore.data
        .map { preferences ->
            preferences[USER_ID] ?: ""
        }

    suspend fun saveUserId(userId: String) {
        application.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    val userName: Flow<String> = application.dataStore.data
        .map { preferences ->
            preferences[USER_NAME] ?: ""
        }

    suspend fun saveUserName(userName: String) {
        application.dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    val token: Flow<String> = application.dataStore.data
        .map { preferences ->
            preferences[TOKEN] ?: ""
        }

    suspend fun saveToken(userId: String) {
        application.dataStore.edit { preferences ->
            preferences[TOKEN] = userId
        }
    }


}