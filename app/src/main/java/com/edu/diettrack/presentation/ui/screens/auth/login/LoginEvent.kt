package com.edu.diettrack.presentation.ui.screens.auth.login

sealed class LoginEvent {
    object PerformLogin : LoginEvent()
}