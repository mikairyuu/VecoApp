package com.veco.vecoapp.presentation.auth

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class AuthState {
    LOGIN,
    REGISTER
}

class AuthHomeViewModel : ViewModel() {
    private val _authState = MutableStateFlow(AuthState.LOGIN)
    val authState: StateFlow<AuthState> = _authState

    fun changeAuthState() {
        _authState.value =
            if (authState.value == AuthState.LOGIN) AuthState.REGISTER else AuthState.LOGIN
    }
}
