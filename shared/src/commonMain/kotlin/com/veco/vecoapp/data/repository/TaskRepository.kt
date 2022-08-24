package com.veco.vecoapp.data.repository

import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.repository.ITaskRepository

class TaskRepository : ITaskRepository {
    override suspend fun getTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(task: Task): Result<Any> {
        TODO("Not yet implemented")
    }
}
