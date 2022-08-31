package com.veco.vecoapp.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.veco.vecoapp.VecoAndroidApp

actual class KeyValueStorage {
    private var sharedPreferences: SharedPreferences =
        VecoAndroidApp.applicationContext.getSharedPreferences("veco_prefs", Context.MODE_PRIVATE)
    private var encSharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        VecoAndroidApp.applicationContext.getApplicationContext(),
        "veco_prefs",
        MasterKey.Builder(VecoAndroidApp.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual fun getString(key: String, encrypted: Boolean): String? {
        val preferences = if (encrypted) encSharedPreferences else sharedPreferences
        return preferences.getString(key, "")
    }

    actual fun getBoolean(key: String, encrypted: Boolean): Boolean {
        val preferences = if (encrypted) encSharedPreferences else sharedPreferences
        return preferences.getBoolean(key, false)
    }

    actual fun getInt(key: String, encrypted: Boolean): Int {
        val preferences = if (encrypted) encSharedPreferences else sharedPreferences
        return preferences.getInt(key, 0)
    }

    actual fun set(key: String, value: String, encrypted: Boolean) {
        val preferences = if (encrypted) encSharedPreferences else sharedPreferences
        preferences.edit().putString(key, value).apply()
    }

    actual fun set(key: String, value: Boolean, encrypted: Boolean) {
        val preferences = if (encrypted) encSharedPreferences else sharedPreferences
        preferences.edit().putBoolean(key, value).apply()
    }

    actual fun set(key: String, value: Int, encrypted: Boolean) {
        val preferences = if (encrypted) encSharedPreferences else sharedPreferences
        preferences.edit().putInt(key, value).apply()
    }

    actual fun contains(key: String, encrypted: Boolean): Boolean {
        val preferences = if (encrypted) encSharedPreferences else sharedPreferences
        return preferences.contains(key)
    }
}
