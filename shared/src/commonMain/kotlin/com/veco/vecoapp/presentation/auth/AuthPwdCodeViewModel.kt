package com.veco.vecoapp.presentation.auth

import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.VecoVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthPwdCodeViewModel : VecoVM() {
    private val _uiState: MutableStateFlow<UIState<Nothing?>> = MutableStateFlow(UIState.Idle())
    val uiState: StateFlow<UIState<Nothing?>> = _uiState

    val code = MutableStateFlow("")

    fun proceed() {
        super.proceed(_uiState) {
            _uiState.emit(UIState.Loading())
            delay(1000)
            _uiState.emit(UIState.Success(null))
        }
    }
}
