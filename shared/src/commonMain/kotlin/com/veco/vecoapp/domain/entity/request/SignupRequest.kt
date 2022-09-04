package com.veco.vecoapp.domain.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(val name: String, val email: String, val password: String)
