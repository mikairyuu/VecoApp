package com.veco.vecoapp.presentation

import com.veco.vecoapp.HttpException
import com.veco.vecoapp.commonLog
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class VecoVM : ViewModel() {
    fun <T> proceed(uiState: MutableStateFlow<UIState<T>>, block: suspend () -> Unit) {
        if (uiState.value is UIState.Loading) return
        viewModelScope.launch {
            uiState.emit(UIState.Loading())
            try {
                block.invoke()
            } catch (e: HttpException) {
                commonLog(msg = e.stackTraceToString())
                uiState.emit(UIState.Error())
            }
        }
    }

    fun setState(textState: MutableStateFlow<String>, string: String) {
        textState.value = string
    }
}
