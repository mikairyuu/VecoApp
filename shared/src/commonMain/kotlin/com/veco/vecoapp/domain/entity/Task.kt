package com.veco.vecoapp.domain.entity

import com.veco.vecoapp.domain.entity.enums.TaskFrequency
import com.veco.vecoapp.domain.entity.enums.TaskStatus
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val deadline: String,
    val type: TaskFrequency,
    val isSeen: Boolean,
    val points: Int,
    val status: TaskStatus
)
