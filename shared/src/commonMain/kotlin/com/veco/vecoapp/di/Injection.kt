package com.veco.vecoapp.di

import com.veco.vecoapp.data.repository.mock.MockTaskRepository
import com.veco.vecoapp.domain.repository.ITaskRepository
import com.veco.vecoapp.storage.KeyValueStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val di = DI {
    bindSingleton {
        HttpClient {
            install(Auth) {
                bearer {
                    loadTokens {
                        // BearerTokens(instance<KeyValueStorage>().getString(KeyDefaults.KEY_USER_TOKEN),"")
                        BearerTokens("asdf", "")
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
            }
        }
    }

    bindSingleton { KeyValueStorage() }

    // repository
    bindSingleton<ITaskRepository> { MockTaskRepository() }
}
