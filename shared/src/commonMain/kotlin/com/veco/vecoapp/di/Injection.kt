package com.veco.vecoapp.di

import com.veco.vecoapp.data.repository.AuthRepository
import com.veco.vecoapp.data.repository.mock.MockTaskRepository
import com.veco.vecoapp.domain.repository.IAuthRepository
import com.veco.vecoapp.domain.repository.ITaskRepository
import com.veco.vecoapp.storage.KeyDefaults
import com.veco.vecoapp.storage.KeyValueStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    bindSingleton {
        HttpClient {
            install(Auth) {
                bearer {
                    loadTokens {
                        val str = instance<KeyValueStorage>().getString(KeyDefaults.KEY_USER_TOKEN)
                        return@loadTokens if (str != null) BearerTokens(
                            str,
                            ""
                        ) else BearerTokens("noToken", "")
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                url("http://lightfire.duckdns.org/")
                header("Content-Type", "application/json")
            }
        }
    }

    bindSingleton { KeyValueStorage() }

    // repository
    bindSingleton<ITaskRepository> { MockTaskRepository() }
    bindSingleton<IAuthRepository> { AuthRepository() }
}
