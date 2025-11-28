package com.edu.diettrack.presentation.ui.screens.auth.signin

sealed class SignInEvent {
    object PerformSignUp : SignInEvent()
}