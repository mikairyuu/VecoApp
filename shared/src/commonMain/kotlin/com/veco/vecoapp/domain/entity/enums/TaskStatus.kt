package com.veco.vecoapp.domain.entity.enums

import com.veco.vecoapp.MR
import com.veco.vecoapp.utils.getStr
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TaskStatus {
    @SerialName("0")
    Uncompleted,
    @SerialName("1")
    InProgress,
    @SerialName("2")
    Completed
}

fun TaskStatus.convert(): StringDesc {
    return when (this) {
        TaskStatus.Uncompleted -> getStr(MR.strings.main_active)
        TaskStatus.InProgress -> getStr(MR.strings.main_check)
        TaskStatus.Completed -> getStr(MR.strings.main_finished)
    }
}
