package com.veco.vecoapp.domain.usecase.user

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.repository.IUserRepository
import org.kodein.di.instance

class GetUserDataUseCase {
    private val repository: IUserRepository by di.instance()

    suspend operator fun invoke() = repository.getUserData()
}
