package com.veco.vecoapp.utils

import com.veco.vecoapp.commonLog
import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.response.Response
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException

expect fun getStr(str: StringResource): StringDesc

suspend inline fun <reified T> HttpClient.safeRequest(
    url: String,
    block: HttpRequestBuilder.() -> Unit = {}
): Response<T> {
    try {
        return request(url, block).body()
    } catch (e: IOException) {
        commonLog(e.stackTraceToString())
        return Response(ResponseResult.NetworkIssue, null)
    }
}
