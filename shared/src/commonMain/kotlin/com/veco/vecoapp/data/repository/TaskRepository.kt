package com.veco.vecoapp.data.repository

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.repository.ITaskRepository
import com.veco.vecoapp.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import org.kodein.di.instance

class TaskRepository : ITaskRepository {
    private val httpClient: HttpClient by di.instance()

    override suspend fun getTasks(): Response<List<Task>> {
        return httpClient.safeRequest("tasks") {
            method = HttpMethod.Get
        }
    }

    override suspend fun completeTask(task: Task): Response<Any> {
        return httpClient.safeRequest("tasks/status") {
            method = HttpMethod.Put
        }
    }
}
