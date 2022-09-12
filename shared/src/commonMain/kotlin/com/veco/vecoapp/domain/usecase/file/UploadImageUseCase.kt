package com.veco.vecoapp.domain.usecase.file

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.domain.repository.IImageRepository
import org.kodein.di.instance

class UploadImageUseCase {
    private val repository: IImageRepository by di.instance()

    suspend operator fun invoke(
        filePath: ByteArray,
        fileName: String,
        onProgress: (Float) -> Unit
    ): Response<Int> =
        repository.uploadImage(filePath, fileName, onProgress)
}
