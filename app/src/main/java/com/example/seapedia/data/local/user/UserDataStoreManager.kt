package com.example.seapedia.data.local.user

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
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
        val USER_ID_KEY = intPreferencesKey("id_user")
        val ROLE_KEY = stringPreferencesKey("role")
        val EMAIL_KEY = stringPreferencesKey("email")
        val PASSWORD_KEY = stringPreferencesKey("password")
    }

    suspend fun setRole(role: UserRole){
        return setPrefs(ROLE_KEY,role.name)
    }

    suspend fun setEmail(email: String){
        return setPrefs(EMAIL_KEY,email)
    }

    suspend fun setUserId(id: Int){
        return setPrefs(USER_ID_KEY,id)
    }

    suspend fun setPassword(password: String){
        return setPrefs(PASSWORD_KEY,password)
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
    fun getEmail() : Flow<String?> {
        return getPrefs(EMAIL_KEY)
    }
    fun getUserId() : Flow<Int?> {
        return getPrefs(USER_ID_KEY)
    }
    fun getPassword() : Flow<String?> {
        return getPrefs(PASSWORD_KEY)
    }


    suspend fun logout(): Boolean {
        removePrefs(ACCESS_TOKEN_KEY)
        removePrefs(PASSWORD_KEY)
        removePrefs(ROLE_KEY)
        removePrefs(EMAIL_KEY)
        removePrefs(USER_ID_KEY)
        return true
    }
}
