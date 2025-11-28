package com.edu.diettrack.presentation.ui.screens.auth

import com.edu.diettrack.domain.model.AuthUser
import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val message: String) : AuthState()
    object Unauthenticated : AuthState()
}
