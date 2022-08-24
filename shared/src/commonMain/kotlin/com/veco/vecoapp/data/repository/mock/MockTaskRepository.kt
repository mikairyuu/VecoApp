package com.veco.vecoapp.data.repository.mock

import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.enums.TaskFrequency
import com.veco.vecoapp.domain.entity.enums.TaskStatus
import com.veco.vecoapp.domain.repository.ITaskRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockTaskRepository : ITaskRepository {
    val tasks = mutableListOf<Task>()

    override suspend fun getTasks(): List<Task> {
        if (tasks.isEmpty()) {
            delay(1000)
            repeat(20) {
                tasks.add(
                    Task(
                        "Сходить в магазин с собственной сумкой",
                        "Огромное количество людей летом проводят свой отдых в парке, часто забывая про" +
                            " санитарные правила, станьте волонтером и очистите свой парк от мусора",
                        "Сегодня, 19:00",
                        TaskFrequency.values().random(),
                        Random.nextInt(200, 2000),
                        TaskStatus.values().random()
                    )
                )
            }
        }
        return tasks
    }

    override suspend fun completeTask(task: Task): Result<Any> {
        TODO("Not yet implemented")
    }
}
