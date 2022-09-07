package com.veco.vecoapp.domain.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponse(
    val id: Int,
    val name: String,
    val email: String,
    val points: Int,
    val isAdmin: Boolean
)
