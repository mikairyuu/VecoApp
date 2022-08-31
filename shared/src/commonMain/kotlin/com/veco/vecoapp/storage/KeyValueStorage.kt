package com.veco.vecoapp.storage

expect class KeyValueStorage () {
    fun getString(key: String, encrypted: Boolean = false): String?
    fun getBoolean(key: String, encrypted: Boolean = false): Boolean
    fun getInt(key: String, encrypted: Boolean = false): Int
    fun set(key: String, value: String, encrypted: Boolean = false)
    fun set(key: String, value: Boolean, encrypted: Boolean = false)
    fun set(key: String, value: Int, encrypted: Boolean = false)
    fun contains(key: String, encrypted: Boolean = false): Boolean
}

object KeyDefaults {
    const val KEY_USER_NAME = "user_name"
    const val KEY_USER_TOKEN = "user_token"
}
