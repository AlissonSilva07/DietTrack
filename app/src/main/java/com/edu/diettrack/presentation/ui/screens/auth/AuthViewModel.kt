package com.edu.diettrack.presentation.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.diettrack.data.utils.Resource
import com.edu.diettrack.domain.repository.AuthRepository
import com.edu.diettrack.presentation.ui.screens.auth.AuthState.Error
import com.edu.diettrack.presentation.ui.screens.auth.AuthState.Success
import com.edu.diettrack.presentation.ui.screens.auth.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    init {
        observeAuthFlow()
    }

    private fun observeAuthFlow() {
        viewModelScope.launch {
            authRepository.authState.collect { user ->
                _state.value = when (user) {
                    null -> AuthState.Unauthenticated
                    else -> AuthState.Success(user)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            when (val result = authRepository.signIn(email, password)) {
                is Resource.Success -> _state.value = Success(result.data)
                is Resource.Error -> _state.value = Error(result.message)
                is Resource.Loading<*> -> {}
            }
        }
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        try {
            _state.value = AuthState.Loading
            val result = authRepository.signUp(email, password)
            if (result is Resource.Success) {
                _state.value = AuthState.Success(result.data)
            }
        } catch (e: Exception) {
            _state.value = AuthState.Error(e.message ?: "Error")
        }
    }

    fun logout() = viewModelScope.launch {
        authRepository.signOut()
        _state.value = AuthState.Unauthenticated
    }
}