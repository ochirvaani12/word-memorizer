package com.example.wordmemorizer.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class WordDatastore(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val WORD_SETTINGS = stringPreferencesKey("word_settings")
        const val TAG = "UserPreferencesRepo"
    }

    val getWordSettings: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[WORD_SETTINGS].toString()
        }

    suspend fun saveWordSettings(wordSettings: String) {
        dataStore.edit { preferences ->
            preferences[WORD_SETTINGS] = wordSettings
        }
    }
}