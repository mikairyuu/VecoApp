package com.veco.vecoapp.presentation

import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.enums.convert
import com.veco.vecoapp.domain.entity.response.Response
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class VecoVM : ViewModel() {
    protected fun <T, K> proceed(
        uiState: MutableStateFlow<UIState<T>>,
        request: suspend () -> Response<K>,
        handleErrors: Boolean = true,
        onSuccess: suspend (Response<K>) -> Unit
    ) {
        if (uiState.value is UIState.Loading) return
        viewModelScope.launch {
            uiState.emit(UIState.Loading())
            val res = request.invoke()
            if (handleErrors) uiState.emit(UIState.Error(res.resultCode.convert()))
            if (res.resultCode == ResponseResult.Success) onSuccess.invoke(res)
        }
    }

    fun setState(textState: MutableStateFlow<String>, string: String) {
        textState.value = string
    }

    val successfulResponse = Response(ResponseResult.Success, null) // TEMP
}
