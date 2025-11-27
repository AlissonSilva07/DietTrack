package com.edu.diettrack.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.diettrack.domain.usecase.GetCurrentUserUseCase
import com.edu.diettrack.domain.usecase.LoginUseCase
import com.edu.diettrack.domain.usecase.LogoutUseCase
import com.edu.diettrack.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        try {
            _state.value = AuthState.Loading
            val user = loginUseCase(email, password)
            _state.value = AuthState.Success(user)
        } catch (e: Exception) {
            _state.value = AuthState.Error(e.message ?: "Error")
        }
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        try {
            _state.value = AuthState.Loading
            val user = signUpUseCase(email, password)
            _state.value = AuthState.Success(user)
        } catch (e: Exception) {
            _state.value = AuthState.Error(e.message ?: "Error")
        }
    }

    fun checkUser() = viewModelScope.launch {
        val user = getCurrentUserUseCase()
        _state.value = if (user != null) {
            AuthState.Success(user)
        } else {
            AuthState.Unauthenticated
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase()
        _state.value = AuthState.Unauthenticated
    }
}