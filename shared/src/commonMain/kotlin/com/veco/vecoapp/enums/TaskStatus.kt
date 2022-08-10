package com.veco.vecoapp.enums

import com.veco.vecoapp.MR
import com.veco.vecoapp.getStr
import dev.icerock.moko.resources.desc.StringDesc

enum class TaskStatus {
    Completed,
    Uncompleted,
    InProgress
}

fun TaskStatus.convert(): StringDesc {
    return when (this) {
        TaskStatus.Completed -> getStr(MR.strings.task_completed)
        TaskStatus.Uncompleted -> getStr(MR.strings.task_uncompleted)
        TaskStatus.InProgress -> getStr(MR.strings.task_inprogress)
    }
}