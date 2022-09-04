package com.veco.vecoapp.data.repository

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.request.LoginRequest
import com.veco.vecoapp.domain.entity.request.SignupRequest
import com.veco.vecoapp.domain.repository.IAuthRepository
import com.veco.vecoapp.handle
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.kodein.di.instance

class AuthRepository : IAuthRepository {
    private val httpClient: HttpClient by di.instance()

    override suspend fun login(email: String, password: String): String {
        return httpClient.request("auth/login") {
            method = HttpMethod.Post
            setBody(LoginRequest(email, password))
        }.handle()
    }

    override suspend fun register(name: String, email: String, password: String): String {
        return httpClient.request("auth/signup") {
            method = HttpMethod.Post
            setBody(SignupRequest(name, email, password))
        }.handle()
    }
}
