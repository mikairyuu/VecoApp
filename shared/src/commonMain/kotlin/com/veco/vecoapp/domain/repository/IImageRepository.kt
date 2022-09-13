package com.veco.vecoapp.domain.repository

import com.veco.vecoapp.domain.entity.response.Response

interface IImageRepository {
    suspend fun uploadImage(filePath: ByteArray, fileName: String, onUpload: (Float) -> Unit): Response<Int>
    suspend fun downloadImage(url: String): Response<ByteArray>
}
