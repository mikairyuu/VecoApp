package com.veco.vecoapp.utils

import android.text.format.DateUtils
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

actual fun getStr(str: StringResource): StringDesc {
    return StringDesc.Resource(str)
}

actual fun getDate(timestamp: Long): String {
    return DateUtils.getRelativeTimeSpanString(timestamp * 1000L).toString()
}
