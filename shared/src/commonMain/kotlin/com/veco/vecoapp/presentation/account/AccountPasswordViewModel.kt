package com.veco.vecoapp.presentation.account

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class PasswordChangeStep {
    OLD_PASSWORD,
    NEW_PASSWORD
}

class AccountPasswordViewModel : ViewModel() {
    private val _step = MutableStateFlow(PasswordChangeStep.OLD_PASSWORD)
    val step: StateFlow<PasswordChangeStep> = _step

    fun changeStep() {
        _step.value =
            if (step.value == PasswordChangeStep.OLD_PASSWORD) PasswordChangeStep.NEW_PASSWORD
            else PasswordChangeStep.OLD_PASSWORD
    }
}
