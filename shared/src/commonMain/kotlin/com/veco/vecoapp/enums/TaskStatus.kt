package com.veco.vecoapp.enums

import com.veco.vecoapp.MR
import com.veco.vecoapp.getStr
import dev.icerock.moko.resources.desc.StringDesc

enum class TaskStatus {
    Uncompleted,
    InProgress,
    Completed
}

fun TaskStatus.convert(): StringDesc {
    return when (this) {
        TaskStatus.Uncompleted -> getStr(MR.strings.main_active)
        TaskStatus.InProgress -> getStr(MR.strings.main_check)
        TaskStatus.Completed -> getStr(MR.strings.main_finished)
    }
}