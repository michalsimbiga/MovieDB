package com.msimbiga.moviesdb.core.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class MoviesLocalDataSource(@ApplicationContext context: Context) {

    private val Context.likedMoviesDataStore: DataStore<Preferences> by
    preferencesDataStore(name = "likedMoviesDataStore")

    private val dataStore = context.likedMoviesDataStore

    suspend fun saveMovieId(id: Int) {
        dataStore.edit { prefs ->
            val currentList = prefs[LIKED_MOVIES_PREF_KEY]?.toMutableSet() ?: mutableSetOf()
            currentList.add(id.toString())
            prefs[LIKED_MOVIES_PREF_KEY] = currentList.toList().toSet()
        }
    }

    suspend fun removeMovieId(id: Int) {
        dataStore.edit { prefs ->
            val currentList = prefs[LIKED_MOVIES_PREF_KEY]?.toMutableSet() ?: mutableSetOf()
            currentList.remove(id.toString())
            prefs[LIKED_MOVIES_PREF_KEY] = currentList.toList().toSet()
        }
    }

    fun getLikedMoviesFlow(): Flow<List<Int>> = dataStore.data.map { prefs ->
        prefs[LIKED_MOVIES_PREF_KEY].orEmpty().map { it.toInt() }
    }

    companion object {
        private val LIKED_MOVIES_PREF_KEY = stringSetPreferencesKey("liked_movies_key")
    }
}