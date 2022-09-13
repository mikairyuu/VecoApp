package com.veco.vecoapp.data.repository

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.repository.IImageRepository
import com.veco.vecoapp.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import org.kodein.di.instance

class ImageRepository : IImageRepository {
    private val httpClient: HttpClient by di.instance()

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

    override suspend fun downloadImage(filePath: String): Response<Any> {
        TODO("Not yet implemented")
    }
}
