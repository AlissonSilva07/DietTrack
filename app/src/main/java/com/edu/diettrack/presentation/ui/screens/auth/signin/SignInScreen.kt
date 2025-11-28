package com.edu.diettrack.presentation.ui.screens.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.edu.diettrack.R
import com.edu.diettrack.presentation.components.AppButton
import com.edu.diettrack.presentation.components.AppPasswordField
import com.edu.diettrack.presentation.components.AppTextField
import com.edu.diettrack.presentation.components.ButtonVariant
import com.edu.diettrack.presentation.navigation.AuthScaffold
import com.edu.diettrack.presentation.ui.screens.auth.AuthState
import com.edu.diettrack.presentation.ui.screens.auth.AuthViewModel
import com.edu.diettrack.presentation.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val signInState by viewModel.signInState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            onNavigateToLogin()
        }
    }

    LaunchedEffect(signInState.snackbarMessage) {
        signInState.snackbarMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
                viewModel.onSnackbarMessageConsumed()
            }
        }
    }

    SignIn(
        modifier = modifier,
        onNavigateToLogin = onNavigateToLogin,
        state = signInState,
        event = viewModel::onSignInEvent
    )
}

@Composable
fun SignIn(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    state: SignInState,
    event: (SignInEvent) -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val logo = if (isDarkTheme) R.drawable.logo_white else R.drawable.logo_black

    val termosString = buildAnnotatedString {
        append("Ao continuar, você concorda com nossos ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Termos de Uso e Serviço")
        }
        append(" e nossa ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Política de Privacidade")
        }
        append(".")
    }

    val loginString = buildAnnotatedString {
        append("Já tem uma conta? ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Faça Login.")
        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Icon(
            painter = painterResource(id = R.drawable.nutrition_48px),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
        Image(
            painter = painterResource(logo),
            contentDescription = null,
            modifier = Modifier.width(180.dp)
        )
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = "Crie uma conta gratuitamente.",
            fontWeight = FontWeight.Bold
        )
        AppTextField(
            label = "Email",
            state = state.email,
            placeholder = "Insira seu endereço de email",
            icon = R.drawable.alternate_email_24px,
            errorText = state.emailError,
            modifier = Modifier.fillMaxWidth()
        )
        AppPasswordField(
            label = "Senha",
            state = state.password,
            placeholder = "Crie uma senha",
            icon = R.drawable.lock_24px,
            errorText = state.passwordError,
            modifier = Modifier.fillMaxWidth()
        )
        AppPasswordField(
            label = "Repetir senha",
            state = state.repeatPassword,
            placeholder = "Digite sua senha novamente",
            icon = R.drawable.lock_24px,
            errorText = state.repeatPasswordError,
            modifier = Modifier.fillMaxWidth()
        )
        AppButton(
            title = "Cadastrar",
            variant = if (state.isLoading) ButtonVariant.DISABLED else ButtonVariant.DEFAULT,
            disabled = state.isLoading,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                event(SignInEvent.PerformSignUp)
            }
        )
        Text(
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            text = termosString,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.weight(1f))
        Text(
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            text = loginString,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable(onClick = onNavigateToLogin)
        )
    }
}

@Preview
@Composable
private fun SignInPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        AuthScaffold { modifier, snackbarHostState ->
            SignInScreen(
                modifier = modifier,
                onNavigateToLogin = {},
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@Preview
@Composable
private fun SignInPreviewDark() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        AuthScaffold { modifier, snackbarHostState ->
            SignInScreen(
                modifier = modifier,
                onNavigateToLogin = {},
                snackbarHostState = snackbarHostState
            )
        }
    }
}