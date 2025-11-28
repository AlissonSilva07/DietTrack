package com.edu.diettrack.presentation.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.diettrack.data.utils.Resource
import com.edu.diettrack.domain.repository.AuthRepository
import com.edu.diettrack.presentation.ui.screens.auth.AuthState.Error
import com.edu.diettrack.presentation.ui.screens.auth.AuthState.Success
import com.edu.diettrack.presentation.ui.screens.auth.login.LoginEvent
import com.edu.diettrack.presentation.ui.screens.auth.login.LoginState
import com.edu.diettrack.presentation.ui.screens.auth.signin.SignInEvent
import com.edu.diettrack.presentation.ui.screens.auth.signin.SignInState
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

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

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

    fun onSignInEvent(event: SignInEvent) {
        when (event) {
            SignInEvent.PerformSignUp -> signUp()
        }
    }

    private fun validateCoreAuthFields(
        emailText: String,
        passwordText: String,
        setEmailError: (String?) -> Unit,
        setPasswordError: (String?) -> Unit
    ): Boolean {
        var isValid = true

        if (emailText.isBlank()) {
            setEmailError("O email deve ser preenchido.")
            isValid = false
        } else if (emailText.length < 6) {
            setEmailError("O email deve ter pelo menos 6 caracteres.")
            isValid = false
        } else if (!isValidEmail(emailText)) {
            setEmailError("Insira um email válido.")
            isValid = false
        } else {
            setEmailError(null)
        }

        if (passwordText.isBlank()) {
            setPasswordError("A senha deve ser preenchida.")
            isValid = false
        } else if (passwordText.length < 3) {
            setPasswordError("A senha deve ter pelo menos 3 caracteres.")
            isValid = false
        } else {
            setPasswordError(null)
        }

        return isValid
    }

    private fun validateLoginFields(): Boolean {
        _loginState.update {
            it.copy(emailError = null, passwordError = null)
        }

        return validateCoreAuthFields(
            emailText = _loginState.value.email.text.toString(),
            passwordText = _loginState.value.password.text.toString(),
            setEmailError = { error ->
                _loginState.update { it.copy(emailError = error) }
            },
            setPasswordError = { error ->
                _loginState.update { it.copy(passwordError = error) }
            }
        )
    }

    private fun validateSignInFields(): Boolean {
        _signInState.update {
            it.copy(emailError = null, passwordError = null, repeatPasswordError = null)
        }

        val passwordText = _signInState.value.password.text.toString()
        val repeatPasswordText = _signInState.value.repeatPassword.text.toString()
        var isValid = true

        val coreValid = validateCoreAuthFields(
            emailText = _signInState.value.email.text.toString(),
            passwordText = passwordText,
            setEmailError = { error ->
                _signInState.update { it.copy(emailError = error) }
            },
            setPasswordError = { error ->
                _signInState.update { it.copy(passwordError = error) }
            }
        )

        if (!coreValid) {
            isValid = false
        }

        if (repeatPasswordText.isBlank()) {
            _signInState.update { it.copy(repeatPasswordError = "A confirmação deve ser preenchida.") }
            isValid = false
        } else if (repeatPasswordText != passwordText) {
            _signInState.update { it.copy(repeatPasswordError = "As senhas não coincidem.") }
            isValid = false
        } else {
            _signInState.update { it.copy(repeatPasswordError = null) }
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

    fun signUp() = viewModelScope.launch {
        if (!validateSignInFields()) {
            _signInState.update { it.copy(isLoading = false) }
            return@launch
        }

        _signInState.update { it.copy(isLoading = true) }

        val email = _signInState.value.email.text.toString()
        val password = _signInState.value.password.text.toString()

        when (val result = authRepository.signUp(email, password)) {
            is Resource.Success -> {
                _state.value = Success(result.data)
                _signInState.update { it.copy(isLoading = false) }
                _signInState.update { it.copy(snackbarMessage = "Sucesso ao registrar a sua conta!") }
            }

            is Resource.Error -> {
                _state.value = Error(result.message)
                _signInState.update {
                    it.copy(
                        isLoading = false,
                        snackbarMessage = result.message
                    )
                }
            }

            is Resource.Loading<*> -> {}
        }
        _signInState.update { it.copy(isLoading = false) }
    }

    fun logout() = viewModelScope.launch {
        authRepository.signOut()
        _state.value = AuthState.Unauthenticated
    }

    fun onSnackbarMessageConsumed() {
        _loginState.update { it.copy(snackbarMessage = null) }
        _signInState.update { it.copy(snackbarMessage = null) }
    }
}