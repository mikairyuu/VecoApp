package com.veco.vecoapp.presentation.auth

import com.veco.vecoapp.presentation.UIState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class AuthState {
    LOGIN,
    REGISTER
}

class AuthHomeViewModel : ViewModel() {
    private val _authState = MutableStateFlow(AuthState.LOGIN)
    val authState: StateFlow<AuthState> = _authState

    private val _uiState: MutableStateFlow<UIState<Nothing?>> = MutableStateFlow(UIState.Idle())
    val uiState: StateFlow<UIState<Nothing?>> = _uiState

    fun changeAuthState() {
        _authState.value =
            if (authState.value == AuthState.LOGIN) AuthState.REGISTER else AuthState.LOGIN
    }

    fun proceed(onProceed: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.emit(UIState.Loading())
            delay(1000)
            val error = false
            if (error) {
                onError("Error")
                _uiState.emit(UIState.Error())
            } else {
                onProceed()
                _uiState.emit(UIState.Success(null))
            }
        }
    }
}
