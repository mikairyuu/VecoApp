package com.veco.vecoapp.di

import com.veco.vecoapp.data.repository.TaskRepository
import com.veco.vecoapp.data.repository.UserRepository
import com.veco.vecoapp.domain.repository.ITaskRepository
import com.veco.vecoapp.domain.repository.IUserRepository
import com.veco.vecoapp.domain.usecase.tasks.GetTasksUseCase
import com.veco.vecoapp.domain.usecase.user.GetUserDataUseCase
import com.veco.vecoapp.domain.usecase.user.LoginUseCase
import com.veco.vecoapp.domain.usecase.user.RegisterUseCase
import com.veco.vecoapp.storage.KeyDefaults
import com.veco.vecoapp.storage.KeyValueStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    bindSingleton {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val str =
                            instance<KeyValueStorage>().getString(KeyDefaults.KEY_USER_TOKEN, true)
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
    bindSingleton<ITaskRepository> { TaskRepository() }
    bindSingleton<IUserRepository> { UserRepository() }

    // usecases

    // tasks
    bindSingleton { GetTasksUseCase() }

    // user
    bindSingleton { LoginUseCase() }
    bindSingleton { RegisterUseCase() }
    bindSingleton { GetUserDataUseCase() }
}
