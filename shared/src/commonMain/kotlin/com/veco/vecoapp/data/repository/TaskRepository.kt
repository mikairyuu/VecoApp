package com.veco.vecoapp.data.repository

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.repository.ITaskRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpMethod
import org.kodein.di.instance

class TaskRepository : ITaskRepository {
    private val httpClient: HttpClient by di.instance()

    override suspend fun getTasks(): List<Task> {
        return httpClient.get("task/all") {
            method = HttpMethod.Get
        }.body()
    }

    override suspend fun completeTask(task: Task): Result<Any> {
        TODO("Not yet implemented")
    }
}
