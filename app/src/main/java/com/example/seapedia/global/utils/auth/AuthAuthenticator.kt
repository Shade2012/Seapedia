package com.example.seapedia.global.utils.auth

import com.example.seapedia.data.local.user.UserDataStoreManager
import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.responses.LoginResponse
import com.example.seapedia.data.remote.services.AuthService
import com.example.seapedia.global.networks.NetworkConstant
import com.example.seapedia.global.utils.UserRole
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


class AuthAuthenticator @Inject constructor(
    private val userDataStoreManager: UserDataStoreManager,
) : Authenticator{
    private suspend fun getNewToken(email:String,password: String,role: String): String {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val json = Json{
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
        val authService = retrofit.create(AuthService::class.java)
        return authService.login(LoginBody(email,password, role = role)).data
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val email = runBlocking {
            userDataStoreManager.getEmail().first()
        }
        val password = runBlocking {
            userDataStoreManager.getPassword().first()
        }
        val role = runBlocking {
            userDataStoreManager.getRole().first()
        }
        return runBlocking {
            val accessToken = try {
                getNewToken(email ?: "",password ?: "", role ?: "")
            }catch (e: Exception){
                userDataStoreManager.logout()
                ""
            }
            userDataStoreManager.setAccessToken(accessToken)
            if(accessToken.isNotEmpty()){
                response.request.newBuilder().header("Authorization","Bearer $accessToken").build()
            }else{
                null
            }
        }
    }
}