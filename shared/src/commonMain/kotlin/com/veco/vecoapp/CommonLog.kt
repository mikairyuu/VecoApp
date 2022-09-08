package com.veco.vecoapp

expect fun commonLog(msg: String, tag: String = "Veco", severity: LogSeverity = LogSeverity.DEBUG)

enum class LogSeverity {
    ERROR,
    INFO,
    DEBUG
}
