package com.veco.vecoapp.di

import com.veco.vecoapp.data.repository.ImageRepository
import com.veco.vecoapp.data.repository.TaskRepository
import com.veco.vecoapp.data.repository.UserRepository
import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.repository.IImageRepository
import com.veco.vecoapp.domain.repository.ITaskRepository
import com.veco.vecoapp.domain.repository.IUserRepository
import com.veco.vecoapp.domain.usecase.file.UploadImageUseCase
import com.veco.vecoapp.domain.usecase.tasks.GetTasksUseCase
import com.veco.vecoapp.domain.usecase.tasks.SubmitTaskUseCase
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
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

fun tokenRetrieval(builder: DirectDI): BearerTokens? {
    builder.apply {
        val str =
            instance<KeyValueStorage>().getString(KeyDefaults.KEY_USER_TOKEN, true)
        return if (str != null) BearerTokens(
            str,
            ""
        ) else null
    }
}

val di = DI {
    bindSingleton {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        tokenRetrieval(this@bindSingleton)
                    }
                    refreshTokens {
                        tokenRetrieval(this@bindSingleton)
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
    bindSingleton<IImageRepository> { ImageRepository() }

    // realms
    bindSingleton(tag = "taskRealm") { Realm.open(RealmConfiguration.Builder(schema = setOf(Task::class)).build()) }

    // usecases

    // tasks
    bindSingleton { GetTasksUseCase() }
    bindSingleton { SubmitTaskUseCase() }

    // user
    bindSingleton { LoginUseCase() }
    bindSingleton { RegisterUseCase() }
    bindSingleton { GetUserDataUseCase() }

    // files
    bindSingleton { UploadImageUseCase() }
}
