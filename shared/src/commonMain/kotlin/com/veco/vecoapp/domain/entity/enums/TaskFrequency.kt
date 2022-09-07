package com.veco.vecoapp.domain.entity.enums

import com.veco.vecoapp.MR
import com.veco.vecoapp.getStr
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TaskFrequency {
    @SerialName("0")
    Daily,
    @SerialName("1")
    Weekly,
    @SerialName("2")
    Monthly
}

fun TaskFrequency.convert(): StringDesc {
    return when (this) {
        TaskFrequency.Daily -> getStr(MR.strings.task_daily)
        TaskFrequency.Weekly -> getStr(MR.strings.task_weekly)
        TaskFrequency.Monthly -> getStr(MR.strings.task_monthly)
    }
}
