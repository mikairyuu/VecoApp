package com.veco.vecoapp.domain.entity.enums

import com.veco.vecoapp.MR
import com.veco.vecoapp.utils.getStr
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ResponseResult {
    @SerialName("0")
    Success,
    @SerialName("1")
    Failed,
    @SerialName("2")
    UserNotFound,
    @SerialName("3")
    PasswordIncorrect,
    @SerialName("4")
    UserAlreadyExists,
    @SerialName("5")
    FileIsEmpty,
    @SerialName("6")
    TaskNotFound,
    NetworkIssue
}

fun ResponseResult.convert(): StringDesc {
    return when (this) {
        ResponseResult.Success -> getStr(MR.strings.status_success)
        ResponseResult.Failed -> getStr(MR.strings.error_failed)
        ResponseResult.UserNotFound -> getStr(MR.strings.error_user_not_found)
        ResponseResult.PasswordIncorrect -> getStr(MR.strings.error_incorrect_pwd)
        ResponseResult.UserAlreadyExists -> getStr(MR.strings.error_user_exists)
        ResponseResult.FileIsEmpty -> getStr(MR.strings.error_file_error)
        ResponseResult.TaskNotFound -> getStr(MR.strings.error_task_not_found)
        ResponseResult.NetworkIssue -> getStr(MR.strings.error_network)
        else -> {
            getStr(MR.strings.string_empty)
        }
    }
}
