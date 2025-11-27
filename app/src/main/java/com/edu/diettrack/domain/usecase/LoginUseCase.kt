package com.edu.diettrack.domain.usecase

import com.edu.diettrack.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authUserRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authUserRepository.signIn(email, password)
}