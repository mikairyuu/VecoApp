package com.veco.vecoapp.domain.repository

import com.veco.vecoapp.domain.entity.Material
import com.veco.vecoapp.domain.entity.response.MaterialPageResponse
import com.veco.vecoapp.domain.entity.response.Response

interface IMaterialRepository {
    suspend fun getMaterialPage(page: Int, pageSize: Int, fromCache: Boolean): Response<MaterialPageResponse>
    suspend fun getMaterial(id: Int, fromCache: Boolean = true): Response<Material>
}
