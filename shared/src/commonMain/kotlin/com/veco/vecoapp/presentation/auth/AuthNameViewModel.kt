package com.veco.vecoapp.presentation.auth

import com.veco.vecoapp.presentation.UIState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthNameViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UIState<Nothing?>> = MutableStateFlow(UIState.Idle())
    val uiState: StateFlow<UIState<Nothing?>> = _uiState

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
