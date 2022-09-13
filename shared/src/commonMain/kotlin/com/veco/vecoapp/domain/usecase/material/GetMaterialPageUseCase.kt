package com.veco.vecoapp.domain.usecase.material

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.repository.IMaterialRepository
import org.kodein.di.instance

class GetMaterialPageUseCase {
    private val repository: IMaterialRepository by di.instance()

    suspend operator fun invoke(page: Int, pageSize: Int, fromCache: Boolean = false) =
        repository.getMaterialPage(page, pageSize, fromCache)
}
