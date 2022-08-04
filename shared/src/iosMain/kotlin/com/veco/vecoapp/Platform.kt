package com.veco.vecoapp

import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val osName = PlatformType.IOS
    actual val osVersion = UIDevice.currentDevice.systemVersion
    actual val deviceModel: String
        get() {
            memScoped {
                val systemInfo: utsname = alloc()
                uname(systemInfo.ptr)
                return NSString.stringWithCString(
                    systemInfo.machine,
                    encoding = NSUTF8StringEncoding
                ) ?: "---"
            }
        }
    actual val cpuType = kotlin.native.Platform.cpuArchitecture.name
    actual val screen: ScreenInfo? = ScreenInfo()
    actual fun logSystemInfo() {
        NSLog("($osName; $osVersion; $deviceModel;${screen!!.width}x${screen.height}@${screen.density}x;$cpuType)")
    }
}

actual class ScreenInfo actual constructor() {
    actual val width =
        CGRectGetWidth(UIScreen.mainScreen.nativeBounds).toInt()
    actual val height =
        CGRectGetHeight(UIScreen.mainScreen.nativeBounds).toInt()
    actual val density = UIScreen.mainScreen.scale.toInt()
}