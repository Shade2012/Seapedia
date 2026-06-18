package com.example.base_compose.domain.repositories



interface UserRepository {
    suspend fun getTokenLocal()
}