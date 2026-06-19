package com.example.seapedia.data.local.user

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseDataStoreManager(
    protected val context: Context
){
    suspend fun <T> setPrefs(key: Preferences.Key<T>,data: T,){
        context.dataStore.edit {
            prefs ->
            prefs[key] = data
        }
    }
    fun <T> getPrefs(key: Preferences.Key<T>): Flow<T?> {
        return context.dataStore.data.map {
            prefs ->
            prefs[key]
        }
    }

    suspend fun <T> removePrefs(key: Preferences.Key<T>): Unit {
        context.dataStore.edit {
            prefs ->
            prefs.remove(key)
        }
    }
}