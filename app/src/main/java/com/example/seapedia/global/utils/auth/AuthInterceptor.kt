package com.example.seapedia.global.utils.auth


import com.example.seapedia.data.local.AuthLocalDataSources
import com.example.seapedia.data.local.user.UserDataStoreManager
import com.example.seapedia.domain.usecases.auth.GetAccessTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val userDataStoreManager: UserDataStoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val accessToken = runBlocking {
            userDataStoreManager.getAccessToken().first()
        }
        val request = chain.request()
            .newBuilder()
            .apply {
                if(!accessToken.isNullOrBlank()){
                    addHeader(
                        "Authorization",
                        "Bearer $accessToken"
                    )
                }
            }.build()
        return chain.proceed(request)
    }
}