package com.veco.vecoapp

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

actual fun getStr(str: StringResource): StringDesc {
    return StringDesc.Resource(str)
}