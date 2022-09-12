package com.veco.vecoapp.presentation

import com.veco.vecoapp.data.PersistentDataManager
import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.enums.convert
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.presentation.misc.AlertType
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class VecoVM : ViewModel() {
    protected fun <T, K> proceed(
        uiState: MutableStateFlow<UIState<T>>?,
        request: suspend () -> Response<K>,
        handleErrors: Boolean = true,
        handler: suspend (Response<K>) -> Unit
    ) {
        if (uiState?.value is UIState.Loading) return
        viewModelScope.launch {
            uiState?.emit(UIState.Loading())
            val res = request.invoke()
            if (res.resultCode == ResponseResult.Success) handler.invoke(res)
            else if (handleErrors) PersistentDataManager.makeAlert(AlertType.CommonAlert(res.resultCode.convert()))
            else handler.invoke(res)
        }
    }

    fun setState(textState: MutableStateFlow<String>, string: String) {
        textState.value = string
    }

    val successfulResponse = Response(ResponseResult.Success, null) // TEMP
}
