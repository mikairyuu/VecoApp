package com.veco.vecoapp.dto

import com.veco.vecoapp.enums.TaskFrequency
import com.veco.vecoapp.enums.TaskStatus

data class Task(
    val title: String,
    val desc: String,
    val deadline: String,
    val frequency: TaskFrequency,
    val points: Int,
    val status: TaskStatus
)