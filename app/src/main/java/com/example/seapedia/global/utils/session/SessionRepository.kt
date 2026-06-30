package com.example.seapedia.global.utils.session

import android.util.Log
import com.example.seapedia.global.utils.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(){
    private val _sessionState = MutableStateFlow(SessionState())
    val sessionState = _sessionState.asStateFlow()

    fun login(
        token: String? = null,
        role: UserRole? = null,
        isLoggedIn: Boolean? = false
    ) {
        _sessionState.value = SessionState(
            isLoggedIn = isLoggedIn == true,
            role = role,
            accessToken = token
        )
        Log.d("Session 1",_sessionState.value.isLoggedIn.toString())
        Log.d("Session 2",_sessionState.value.role.toString())
    }

    fun setPhoneNumber(isValidBuyer: Boolean){

    }

    fun setValid(isValidBuyer: Boolean){
        _sessionState.update {
            it.copy(
                isValidBuyer = isValidBuyer
            )
        }
    }

    fun logout() {
        _sessionState.value = SessionState()
    }
}