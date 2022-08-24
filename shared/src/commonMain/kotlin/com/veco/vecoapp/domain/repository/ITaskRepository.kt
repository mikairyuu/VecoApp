package com.veco.vecoapp.domain.repository

import com.veco.vecoapp.domain.entity.Task

interface ITaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun completeTask(task: Task): Result<Any>
}
