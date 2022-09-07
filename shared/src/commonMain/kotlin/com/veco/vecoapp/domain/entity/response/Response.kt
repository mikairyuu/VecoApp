package com.veco.vecoapp.domain.entity.response

import com.veco.vecoapp.domain.entity.enums.ResponseResult
import kotlinx.serialization.Serializable

@Serializable
data class Response <T> (
    val resultCode: ResponseResult,
    val data: T?
)
