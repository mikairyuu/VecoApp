package com.veco.vecoapp.data.repository

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.repository.ITaskRepository
import com.veco.vecoapp.handle
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import org.kodein.di.instance

class TaskRepository : ITaskRepository {
    private val httpClient: HttpClient by di.instance()

    override suspend fun getTasks(): Response<List<Task>> {
        return httpClient.request("tasks/all") {
            method = HttpMethod.Get
        }.handle()
    }

    override suspend fun completeTask(task: Task): Response<Any> {
        return httpClient.request("tasks/status") {
            method = HttpMethod.Put
        }.handle()
    }
}
