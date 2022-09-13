package com.veco.vecoapp.domain.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class SubmitTaskRequest(val imageId: List<Int>, val taskId: Int)
