package com.veco.vecoapp.presentation.account

import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.VecoVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class PasswordChangeStep {
    OLD_PASSWORD,
    NEW_PASSWORD
}

class AccountPasswordViewModel : VecoVM() {
    val oldPwd = MutableStateFlow("")

    val newPwd = MutableStateFlow("")

    val newPwdConf = MutableStateFlow("")

    private val _uiState: MutableStateFlow<UIState<Nothing?>> = MutableStateFlow(UIState.Idle())
    val uiState: StateFlow<UIState<Nothing?>> = _uiState

    private val _step = MutableStateFlow(PasswordChangeStep.OLD_PASSWORD)
    val step: StateFlow<PasswordChangeStep> = _step

    fun changeStep() {
        if (uiState.value is UIState.Loading<*>) return
        _step.value =
            if (step.value == PasswordChangeStep.OLD_PASSWORD) PasswordChangeStep.NEW_PASSWORD
            else PasswordChangeStep.OLD_PASSWORD
    }
}
