package com.example.seapedia.data.local.user

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.seapedia.global.utils.UserRole
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "user_prefs")
@Singleton
class UserDataStoreManager @Inject constructor(
    @ApplicationContext context: Context
) : BaseDataStoreManager(context) {
    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val ROLE_KEY = stringPreferencesKey("role")
    }

    suspend fun setRole(role: UserRole){
        return setPrefs(ROLE_KEY,role.name)
    }

    suspend fun setAccessToken(token: String){
        return setPrefs(ACCESS_TOKEN_KEY,token)
    }

    fun getRole() : Flow<String?> {
        return getPrefs(ROLE_KEY)
    }

    fun getAccessToken() : Flow<String?> {
        return getPrefs(ACCESS_TOKEN_KEY)
    }

    suspend fun logout(): Boolean {
        removePrefs(ACCESS_TOKEN_KEY)
        removePrefs(ROLE_KEY)
        return true
    }
}