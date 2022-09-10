package com.veco.vecoapp.data

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.entity.response.UserDataResponse
import com.veco.vecoapp.domain.usecase.user.GetUserDataUseCase
import com.veco.vecoapp.presentation.misc.Alert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance

object PersistentDataManager {
    private val getUserDataUseCase: GetUserDataUseCase by di.instance()
    private val persistentScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _userData = MutableStateFlow<UserDataResponse?>(null)
    val userData: StateFlow<UserDataResponse?> = _userData

    private val _connected = MutableStateFlow(false)
    val connected: StateFlow<Boolean> = _connected

    private val _login = MutableStateFlow(false)
    val login: StateFlow<Boolean> = _login

    val alert = MutableSharedFlow<Alert?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    fun requestUserDataUpdate() {
        if (connected.value && login.value) {
            persistentScope.launch {
                val response = getUserDataUseCase()
                _userData.emit(response.data)
            }
        }
    }

    fun updateConnection(newConnectionStatus: Boolean) {
        _connected.value = newConnectionStatus
    }

    fun updateLogin(loginStatus: Boolean) {
        _login.value = loginStatus
    }

    suspend fun makeAlert(alertToMake: Alert) {
        alert.emit(alertToMake)
    }
}
