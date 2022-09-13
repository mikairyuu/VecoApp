package com.veco.vecoapp.utils

import android.text.format.DateUtils
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import java.text.SimpleDateFormat
import java.util.Locale

actual fun getStr(str: StringResource): StringDesc {
    return StringDesc.Resource(str)
}

actual fun getRelativeDate(timestamp: Long): String {
    return DateUtils.getRelativeTimeSpanString(timestamp * 1000L).toString()
}

actual fun getDate(timestamp: Long): String {
    return SimpleDateFormat("d MMM", Locale.getDefault()).format(timestamp * 1000L).toString()
}
