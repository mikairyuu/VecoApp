package com.veco.vecoapp.presentation.misc

import com.veco.vecoapp.data.PersistentDataManager
import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.usecase.file.UploadImageUseCase
import com.veco.vecoapp.domain.usecase.tasks.SubmitTaskUseCase
import com.veco.vecoapp.presentation.VecoVM
import org.kodein.di.instance

class ConfirmViewModel : VecoVM() {
    private val uploadImageUseCase: UploadImageUseCase by di.instance()
    private val submitTaskUseCase: SubmitTaskUseCase by di.instance()

    fun uploadPicture(
        file: ByteArray,
        fileName: String,
        onDone: (Int?) -> Unit,
        onProgress: (Float) -> Unit
    ) {
        super.proceed<Any, Int>(null, request = {
            uploadImageUseCase.invoke(file, fileName, onProgress)
        }, handleErrors = true, {
            onDone(it.data)
        })
    }

    fun proceed(ids: List<Int>, taskId: Int, navigateHome: () -> Unit) {
        super.proceed<Any, Int>(null, request = {
            submitTaskUseCase.invoke(ids, taskId)
        }, handleErrors = true, {
            PersistentDataManager.makeAlert(AlertType.TaskCompletion)
            navigateHome()
        })
    }
}
