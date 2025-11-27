package com.edu.diettrack.presentation.ui.screens

import com.edu.diettrack.domain.model.AuthUser

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val user: AuthUser?) : AuthState()
    data class Error(val message: String) : AuthState()
    object Unauthenticated : AuthState()
}
