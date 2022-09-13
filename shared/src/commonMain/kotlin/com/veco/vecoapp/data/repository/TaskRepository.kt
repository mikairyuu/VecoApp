package com.veco.vecoapp.data.repository

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.Task
import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.request.SubmitTaskRequest
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.repository.ITaskRepository
import com.veco.vecoapp.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.kodein.di.instance

class TaskRepository : ITaskRepository {
    private val httpClient: HttpClient by di.instance()
    private val taskRealm: Realm by di.instance(tag = "taskRealm")

    override suspend fun getTasks(forceSync: Boolean): Response<List<Task>> {
        val response: Response<List<Task>>
        val cachedTasks = taskRealm.query<Task>().find().toList()
        if (cachedTasks.isNotEmpty() && !forceSync) {
            response = Response(ResponseResult.Success, cachedTasks)
        } else {
            response = httpClient.safeRequest("tasks") {
                method = HttpMethod.Get
            }
            taskRealm.write {
                response.data?.forEach {
                    copyToRealm(it, UpdatePolicy.ALL)
                }
            }
        }
        return response
    }

    override suspend fun submitTask(imageIds: List<Int>, taskId: Int): Response<Int> {
        return httpClient.safeRequest("task/images") {
            method = HttpMethod.Post
            setBody(SubmitTaskRequest(imageIds, taskId))
        }
    }

    override suspend fun completeTask(task: Task): Response<Any> {
        return httpClient.safeRequest("task/status") {
            method = HttpMethod.Put
        }
    }
}
