package com.veco.vecoapp

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

expect fun getStr(str: StringResource): StringDesc

// A simple response wrapper that will throw error on non-200 response
suspend inline fun <reified T> HttpResponse.handle(): T {
    if (this.status != HttpStatusCode.OK) {
        throw HttpException(this.status.value)
    } else {
        return this.body()
    }
}

class HttpException(val code: Int) : Exception()
