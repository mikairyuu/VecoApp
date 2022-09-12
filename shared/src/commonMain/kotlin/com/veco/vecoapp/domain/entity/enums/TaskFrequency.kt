package com.veco.vecoapp.domain.entity.enums

import com.veco.vecoapp.MR
import com.veco.vecoapp.utils.getStr
import dev.icerock.moko.resources.desc.StringDesc

object TaskFrequency {
    const val Daily: Int = 0
    const val Weekly: Int = 1
    const val Monthly: Int = 2
}

fun TaskFrequency.convert(value: Int): StringDesc {
    return when (value) {
        Daily -> getStr(MR.strings.task_daily)
        Weekly -> getStr(MR.strings.task_weekly)
        Monthly -> getStr(MR.strings.task_monthly)
        else -> {
            return getStr(MR.strings.string_empty)
        }
    }
}
