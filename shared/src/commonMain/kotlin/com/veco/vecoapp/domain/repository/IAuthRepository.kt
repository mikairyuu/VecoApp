package com.veco.vecoapp.domain.repository

interface IAuthRepository {
    suspend fun login(email: String, password: String): String
    suspend fun register(name: String, email: String, password: String): String
}
