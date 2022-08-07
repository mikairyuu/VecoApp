package com.veco.vecoapp

import android.content.res.Resources
import android.os.Build
import android.util.Log
import kotlin.math.round

actual object Platform {
    actual val osName = PlatformType.Android

    actual val osVersion = "${Build.VERSION.SDK_INT}"

    actual val deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}"

    actual val cpuType = Build.SUPPORTED_ABIS.firstOrNull() ?: "---"

    actual val screen: ScreenInfo? = ScreenInfo()

    actual fun logSystemInfo() {
        Log.d(
            "Platform",
            "($osName; $osVersion; $deviceModel; ${screen!!.width}x${screen.height}@${screen.density}x; $cpuType)"
        )
    }
}

actual class ScreenInfo actual constructor() {
    private val metrics = Resources.getSystem().displayMetrics

    actual val width = metrics.widthPixels
    actual val height = metrics.heightPixels
    actual val density = round(metrics.density).toInt()
}