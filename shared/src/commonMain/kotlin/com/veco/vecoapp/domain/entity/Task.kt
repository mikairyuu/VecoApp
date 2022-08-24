package com.veco.vecoapp.domain.entity

import com.veco.vecoapp.domain.entity.enums.TaskFrequency
import com.veco.vecoapp.domain.entity.enums.TaskStatus

data class Task(
    val title: String,
    val desc: String,
    val deadline: String,
    val frequency: TaskFrequency,
    val points: Int,
    val status: TaskStatus
)
