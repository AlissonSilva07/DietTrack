package com.edu.diettrack.presentation.ui.screens.auth.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val emailError: String? = null,
    val password: TextFieldState = TextFieldState(),
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val snackbarMessage: String? = null
)