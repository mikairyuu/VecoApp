package com.veco.vecoapp.presentation.auth

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.usecase.user.LoginUseCase
import com.veco.vecoapp.presentation.UIState
import com.veco.vecoapp.presentation.VecoVM
import com.veco.vecoapp.storage.KeyDefaults
import com.veco.vecoapp.storage.KeyValueStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.kodein.di.direct
import org.kodein.di.instance

enum class AuthState {
    LOGIN,
    REGISTER
}

class AuthHomeViewModel : VecoVM() {
    private val _authState = MutableStateFlow(AuthState.LOGIN)
    val authState: StateFlow<AuthState> = _authState

    private val _uiState: MutableStateFlow<UIState<Nothing?>> = MutableStateFlow(UIState.Idle())
    val uiState: StateFlow<UIState<Nothing?>> = _uiState

    val email = MutableStateFlow("")

    val password = MutableStateFlow("")

    val passwordConf = MutableStateFlow("")

    private val _passwordConfValidState = MutableStateFlow(false)
    val passwordConfValidState: StateFlow<Boolean> = _passwordConfValidState

    private val _buttonEnabledState = MutableStateFlow(false)
    val buttonEnabledState: StateFlow<Boolean> = _buttonEnabledState

    fun setAndValidate(state: MutableStateFlow<String>, value: String) {
        setState(state, value)
        validate()
    }

    private fun validate() {
        validatePasswordConf()
        checkButtonEnabled()
    }

    private fun checkButtonEnabled() {
        _buttonEnabledState.value = when (_authState.value) {
            AuthState.LOGIN -> email.value.isNotEmpty() && password.value.isNotEmpty()
            AuthState.REGISTER -> email.value.isNotEmpty() && password.value.isNotEmpty() &&
                passwordConf.value.isNotEmpty() && _passwordConfValidState.value
        }
    }

    private fun validatePasswordConf() {
        _passwordConfValidState.value = passwordConf.value != password.value
    }

    fun changeAuthState() {
        if (uiState.value is UIState.Loading) return
        _authState.value =
            if (authState.value == AuthState.LOGIN) AuthState.REGISTER else AuthState.LOGIN
        validate()
    }

    fun proceed() {
        if (authState.value == AuthState.LOGIN)
            super.proceed(
                _uiState,
                request = {
                    LoginUseCase()(
                        email.value,
                        password.value
                    )
                }, handleErrors = true,
                block = {
                    if (authState.value == AuthState.LOGIN) {
                        di.direct.instance<KeyValueStorage>()
                            .set(KeyDefaults.KEY_USER_TOKEN, it.data!!, true)
                    }
                    _uiState.emit(UIState.Success(null))
                }
            )
        else _uiState.value = UIState.Success(null)
    }
}
