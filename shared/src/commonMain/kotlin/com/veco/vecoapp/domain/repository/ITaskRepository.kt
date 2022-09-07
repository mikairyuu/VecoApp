package com.veco.vecoapp.domain.repository

import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.response.Response

interface ITaskRepository {
    suspend fun getTasks(): Response<List<Task>>
    suspend fun completeTask(task: Task): Response<Any>
}
