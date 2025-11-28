package com.edu.diettrack.presentation.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.diettrack.data.utils.Resource
import com.edu.diettrack.domain.repository.AuthRepository
import com.edu.diettrack.presentation.ui.screens.auth.AuthState.Error
import com.edu.diettrack.presentation.ui.screens.auth.AuthState.Success
import com.edu.diettrack.presentation.ui.screens.auth.login.LoginEvent
import com.edu.diettrack.presentation.ui.screens.auth.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    fun onLoginEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.PerformLogin -> login()
        }
    }

    private fun validateLoginFields(): Boolean {
        var isValid = true

        _loginState.update {
            it.copy(
                emailError = null,
                passwordError = null
            )
        }

        val emailText = _loginState.value.email.text
        val passwordText = _loginState.value.password.text


        if (emailText.isBlank()) {
            _loginState.update { it.copy(emailError = "O email deve ser preenchido.") }
            isValid = false
        } else if (emailText.length < 6) {
            _loginState.update { it.copy(emailError = "O email deve ter pelo menos 6 caracteres.") }
            isValid = false
        } else if (!isValidEmail(emailText)) {
            _loginState.update { it.copy(emailError = "Insira um email válido.") }
            isValid = false
        }

        if (passwordText.isBlank()) {
            _loginState.update { it.copy(passwordError = "A senha deve ser preenchida.") }
            isValid = false
        } else if (passwordText.length < 3) {
            _loginState.update { it.copy(passwordError = "A senha deve ter pelo menos 3 caracteres.") }
            isValid = false
        }

        return isValid
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !target.isNullOrEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    fun login() {
        viewModelScope.launch {
            if (!validateLoginFields()) {
                _loginState.update { it.copy(isLoading = false) }
                return@launch
            }

            _loginState.update { it.copy(isLoading = true) }

            when (val result = authRepository.signIn(
                _loginState.value.email.text.toString(),
                _loginState.value.password.text.toString()
            )) {
                is Resource.Success -> {
                    _state.value = Success(result.data)
                    _loginState.update { it.copy(isLoading = false) }
                }

                is Resource.Error -> {
                    _state.value = Error(result.message)
                    _loginState.update {
                        it.copy(
                            isLoading = false,
                            snackbarMessage = result.message
                        )
                    }
                }

                is Resource.Loading<*> -> {}
            }
            _loginState.update { it.copy(isLoading = false) }
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

    fun onSnackbarMessageConsumed() {
        _loginState.update { it.copy(snackbarMessage = null) }
    }
}