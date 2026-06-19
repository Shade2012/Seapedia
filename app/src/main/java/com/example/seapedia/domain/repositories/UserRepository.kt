package com.example.seapedia.domain.repositories



interface UserRepository {
    suspend fun getTokenLocal()
}