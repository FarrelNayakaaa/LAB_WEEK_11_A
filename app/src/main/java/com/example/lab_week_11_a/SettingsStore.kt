package com.example.lab_week_11_a

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension DataStore
val Context.dataStore by preferencesDataStore(name = "settingsStore")

class SettingsStore(private val context: Context) {

    private val KEY_TEXT = stringPreferencesKey("key_text")

    // Flow<String> — data stream dari DataStore
    val text: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_TEXT] ?: ""
        }

    // Save text ke DataStore
    suspend fun saveText(text: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_TEXT] = text
        }
    }
}
