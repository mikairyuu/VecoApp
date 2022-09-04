package com.veco.vecoapp.domain.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val email: String, val password: String)
