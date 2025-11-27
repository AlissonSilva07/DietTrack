package com.edu.diettrack.domain.usecase

import com.edu.diettrack.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authUserRepository: AuthRepository
) {
    suspend operator fun invoke() = authUserRepository.getCurrentUser()
}