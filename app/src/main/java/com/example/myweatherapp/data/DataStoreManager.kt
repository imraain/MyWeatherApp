package com.example.myweatherapp.data

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

private val Context.datastore by preferecesDataStore(name = "weatherPreference")

class WeatherPreferenceManager(private val context: Context) {

    companion object {
        private val CITYNAME_KEY = stringPreferencesKey("cityname")
    }

    val citynameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        // retrieve the cityname value, returning null if not set
        preferences[CITYNAME_KEY]
    }
}