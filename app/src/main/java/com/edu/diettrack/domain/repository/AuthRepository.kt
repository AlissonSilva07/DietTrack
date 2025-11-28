package com.edu.diettrack.domain.repository

import com.edu.diettrack.data.utils.Resource
import com.edu.diettrack.domain.model.AuthUser
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authState: StateFlow<FirebaseUser?>
    suspend fun signIn(email: String, password: String): Resource<FirebaseUser?>
    suspend fun signUp(email: String, password: String): Resource<FirebaseUser?>
    suspend fun getCurrentUser(): AuthUser?
    suspend fun signOut()
}