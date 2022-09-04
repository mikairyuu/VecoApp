package com.veco.vecoapp.domain.usecase.auth

import com.veco.vecoapp.di.di
import com.veco.vecoapp.domain.repository.IAuthRepository
import org.kodein.di.instance

class RegisterUseCase {
    private val repository: IAuthRepository by di.instance()

    suspend operator fun invoke(name: String, email: String, password: String) =
        repository.register(name, email, password)
}
