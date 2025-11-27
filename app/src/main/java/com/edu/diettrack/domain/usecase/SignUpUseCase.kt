package com.edu.diettrack.domain.usecase

import com.edu.diettrack.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authUserRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authUserRepository.signUp(email, password)
}