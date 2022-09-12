package com.veco.vecoapp.domain.entity.enums

import com.veco.vecoapp.MR
import com.veco.vecoapp.utils.getStr
import dev.icerock.moko.resources.desc.StringDesc

object TaskStatus {
    const val Uncompleted: Int = 0
    const val InProgress: Int = 1
    const val Completed: Int = 2
    val values: List<Int> = listOf(Uncompleted, InProgress, Completed)
}

fun TaskStatus.convert(value: Int): StringDesc {
    return when (value) {
        Uncompleted -> getStr(MR.strings.main_active)
        InProgress -> getStr(MR.strings.main_check)
        Completed -> getStr(MR.strings.main_finished)
        else -> {
            return getStr(MR.strings.string_empty)
        }
    }
}
