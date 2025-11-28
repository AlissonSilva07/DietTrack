package com.edu.diettrack.presentation.ui.screens.auth.signin

import androidx.compose.foundation.text.input.TextFieldState

data class SignInState(
    val email: TextFieldState = TextFieldState(),
    val emailError: String? = null,
    val password: TextFieldState = TextFieldState(),
    val passwordError: String? = null,
    val repeatPassword: TextFieldState = TextFieldState(),
    val repeatPasswordError: String? = null,
    val isLoading: Boolean = false,
    val snackbarMessage: String? = null
)