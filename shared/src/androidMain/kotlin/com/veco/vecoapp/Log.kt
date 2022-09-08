package com.veco.vecoapp

import android.util.Log

actual fun commonLog(msg: String, tag: String, severity: LogSeverity) {
    when (severity) {
        LogSeverity.ERROR -> Log.e(tag, msg)
        LogSeverity.INFO -> Log.i(tag, msg)
        LogSeverity.DEBUG -> Log.d(tag, msg)
        else -> {
            Log.d(tag, msg)
        }
    }
}
