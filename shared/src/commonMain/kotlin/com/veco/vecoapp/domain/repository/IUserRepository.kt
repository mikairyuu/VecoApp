package com.veco.vecoapp.domain.repository

import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.entity.response.UserDataResponse

interface IUserRepository {
    suspend fun login(email: String, password: String): Response<String>
    suspend fun register(name: String, email: String, password: String): Response<String>
    suspend fun getUserData(): Response<UserDataResponse>
}
