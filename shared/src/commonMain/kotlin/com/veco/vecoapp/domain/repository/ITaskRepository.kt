package com.veco.vecoapp.domain.repository

import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.response.Response

interface ITaskRepository {
    suspend fun getTasks(forceSync: Boolean): Response<List<Task>>
    suspend fun submitTask(imageIds: List<Int>, taskId: Int): Response<Int>
    suspend fun completeTask(task: Task): Response<Any>
}
