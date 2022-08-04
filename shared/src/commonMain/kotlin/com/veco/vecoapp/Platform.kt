package com.veco.vecoapp

expect class Platform() {
    val osName: PlatformType
    val osVersion: String
    val deviceModel: String
    val cpuType: String
    val screen: ScreenInfo?
    fun logSystemInfo()
}

expect class ScreenInfo() {
    val width: Int
    val height: Int
    val density: Int
}

enum class PlatformType {
    Android,
    IOS
}