package com.veco.vecoapp.domain.usecase.user

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.repository.IUserRepository
import org.kodein.di.instance

class RegisterUseCase {
    private val repository: IUserRepository by di.instance()

    suspend operator fun invoke(name: String, email: String, password: String) =
        repository.register(name, email, password)
}
