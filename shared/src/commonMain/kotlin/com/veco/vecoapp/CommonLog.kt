package com.veco.vecoapp

expect fun commonLog(tag: String = "", msg: String, severity: LogSeverity = LogSeverity.DEBUG)

enum class LogSeverity {
    ERROR,
    INFO,
    DEBUG
}