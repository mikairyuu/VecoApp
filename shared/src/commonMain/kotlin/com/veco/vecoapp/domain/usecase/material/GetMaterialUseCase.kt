package com.veco.vecoapp.domain.usecase.material

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.repository.IMaterialRepository
import org.kodein.di.instance

class GetMaterialUseCase {
    private val repository: IMaterialRepository by di.instance()

    suspend operator fun invoke(id: Int, fromCache: Boolean) =
        repository.getMaterial(id, fromCache)
}
