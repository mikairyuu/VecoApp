package com.veco.vecoapp.presentation

import com.veco.vecoapp.HttpException
import com.veco.vecoapp.MR
import com.veco.vecoapp.commonLog
import com.veco.vecoapp.domain.entity.enums.ResponseResult
import com.veco.vecoapp.domain.entity.enums.convert
import com.veco.vecoapp.domain.entity.response.Response
import com.veco.vecoapp.getStr
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class VecoVM : ViewModel() {
    fun <T, K> proceed(
        uiState: MutableStateFlow<UIState<T>>,
        request: suspend () -> Response<K>,
        handleErrors: Boolean = true,
        block: suspend (Response<K>) -> Unit
    ) {
        if (uiState.value is UIState.Loading) return
        viewModelScope.launch {
            uiState.emit(UIState.Loading())
            try {
                val res = request.invoke()
                if (handleErrors) uiState.emit(UIState.Error(res.resultCode.convert()))
                block.invoke(res)
            } catch (e: HttpException) {
                uiState.emit(UIState.Error(getStr(MR.strings.error_failed)))
                commonLog(msg = e.stackTraceToString())
            }
        }
    }

    fun setState(textState: MutableStateFlow<String>, string: String) {
        textState.value = string
    }

    val successfulResponse = Response(ResponseResult.Success, null) // TEMP
}
