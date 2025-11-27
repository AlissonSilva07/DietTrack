package com.edu.diettrack.domain.repository

import com.edu.diettrack.domain.model.AuthUser

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AuthUser
    suspend fun signUp(email: String, password: String): AuthUser?
    suspend fun getCurrentUser(): AuthUser?
    suspend fun signOut()
}