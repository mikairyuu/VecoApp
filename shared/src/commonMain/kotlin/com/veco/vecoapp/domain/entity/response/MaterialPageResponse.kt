package com.veco.vecoapp.domain.entity.response

import com.veco.vecoapp.domain.entity.Material
import kotlinx.serialization.Serializable

@Serializable
data class MaterialPageResponse(var data: List<Material>, var isNext: Boolean)
