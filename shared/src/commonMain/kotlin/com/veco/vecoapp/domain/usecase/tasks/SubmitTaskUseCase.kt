package com.veco.vecoapp.domain.usecase.tasks

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.repository.ITaskRepository
import org.kodein.di.instance

class SubmitTaskUseCase {
    private val repository: ITaskRepository by di.instance()

    suspend operator fun invoke(
        imageIds: List<Int>,
        taskId: Int,
    ): Response<Int> =
        repository.submitTask(imageIds, taskId)
}
