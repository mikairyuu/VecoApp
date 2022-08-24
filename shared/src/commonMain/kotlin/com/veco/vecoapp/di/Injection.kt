package com.veco.vecoapp.di

import com.veco.vecoapp.data.repository.mock.MockTaskRepository
import com.veco.vecoapp.domain.repository.ITaskRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val di = DI {
    bindSingleton {
        HttpClient {
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

    // repository
    bindSingleton<ITaskRepository> { MockTaskRepository() }
}
