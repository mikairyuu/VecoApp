package com.veco.vecoapp.data.repository

import com.veco.vecoapp.commonLog
import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.CacheFile
import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.repository.IImageRepository
import com.veco.vecoapp.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.utils.io.errors.IOException
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.kodein.di.instance

class ImageRepository : IImageRepository {
    private val httpClient: HttpClient by di.instance()
    private val fileRealm: Realm by di.instance(tag = "fileRealm")

    override suspend fun uploadImage(
        filePath: ByteArray,
        fileName: String,
        onUpload: (Float) -> Unit
    ): Response<Int> {
        return httpClient.safeRequest("box") {
            method = HttpMethod.Post
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "file", filePath,
                            Headers.build {
                                append(HttpHeaders.ContentType, "image/webp")
                                append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                            }
                        )
                    },
                    boundary = "WebAppBoundary"
                )
            )
            onUpload { bytesSentTotal, contentLength ->
                onUpload(bytesSentTotal.toFloat() / contentLength)
            }
        }
    }

    override suspend fun downloadImage(url: String): Response<ByteArray> {
        var response = fileRealm.query<CacheFile>("url = $0", url).first().find()?.data
        try {
            if (response == null) {
                response = httpClient.get(url).body()
                val file = CacheFile().apply { this.url = url; this.data = response!! }
                fileRealm.writeBlocking {
                    copyToRealm(file, UpdatePolicy.ALL)
                }
            }
            return Response(ResponseResult.Success, response)
        } catch (e: IOException) {
            commonLog(e.stackTraceToString())
            return Response(ResponseResult.NetworkIssue, null)
        }
    }
}
