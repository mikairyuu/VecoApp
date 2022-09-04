package com.veco.vecoapp.presentation.account

import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.VecoVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AccountDataViewModel : VecoVM() {
    val name = MutableStateFlow("")

    val email = MutableStateFlow("")

    private val _uiState: MutableStateFlow<UIState<Nothing?>> = MutableStateFlow(UIState.Idle())
    val uiState: StateFlow<UIState<Nothing?>> = _uiState

    fun proceed() {
        super.proceed(_uiState) {
            delay(1000)
            _uiState.emit(UIState.Success(null))
        }
    }
}
