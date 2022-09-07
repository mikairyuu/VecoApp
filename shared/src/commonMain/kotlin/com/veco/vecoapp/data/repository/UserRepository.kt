package com.veco.vecoapp.data.repository

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.request.LoginRequest
import com.veco.vecoapp.domain.entity.request.SignupRequest
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.entity.response.UserDataResponse
import com.veco.vecoapp.domain.repository.IUserRepository
import com.veco.vecoapp.handle
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.kodein.di.instance

class UserRepository : IUserRepository {
    private val httpClient: HttpClient by di.instance()

    override suspend fun login(email: String, password: String): Response<String> {
        return httpClient.request("auth/login") {
            method = HttpMethod.Post
            setBody(LoginRequest(email, password))
        }.handle()
    }

    override suspend fun register(name: String, email: String, password: String): Response<String> {
        return httpClient.request("auth/signup") {
            method = HttpMethod.Post
            setBody(SignupRequest(name, email, password))
        }.handle()
    }

    override suspend fun getUserData(): Response<UserDataResponse> {
        return httpClient.request("user") {
            method = HttpMethod.Get
        }.handle()
    }
}
