package com.veco.vecoapp.presentation.account

import com.veco.vecoapp.data.PersistentDataManager
import com.veco.vecoapp.di.di
import com.veco.vecoapp.presentation.VecoVM
import com.veco.vecoapp.storage.KeyDefaults
import com.veco.vecoapp.storage.KeyValueStorage
import org.kodein.di.instance

class AccountHomeViewModel : VecoVM() {
    val keyValueStorage: KeyValueStorage by di.instance()
    fun onLogout() {
        PersistentDataManager.updateLogin(false)
        keyValueStorage.set(KeyDefaults.KEY_USER_TOKEN, "", true)
    }
}
