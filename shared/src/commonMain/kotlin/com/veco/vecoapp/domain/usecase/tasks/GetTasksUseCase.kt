package com.veco.vecoapp.domain.usecase.tasks

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.repository.ITaskRepository
import org.kodein.di.instance

class GetTasksUseCase {
    private val repository: ITaskRepository by di.instance()

    suspend operator fun invoke() = repository.getTasks()
}
