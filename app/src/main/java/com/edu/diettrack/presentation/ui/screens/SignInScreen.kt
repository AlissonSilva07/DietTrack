package com.edu.diettrack.presentation.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.diettrack.R
import com.edu.diettrack.presentation.components.AppPasswordField
import com.edu.diettrack.presentation.components.AppTextField
import com.edu.diettrack.presentation.navigation.AuthScaffold
import com.edu.diettrack.presentation.ui.theme.AppTheme

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit
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
        withLink(
            LinkAnnotation.Url(
                "Faça Login",
                TextLinkStyles(style = SpanStyle(fontWeight = FontWeight.Bold)),
            )
        ) {
            append("Faça Login")
        }
        append(".")
    }

    val emailText = rememberTextFieldState()
    val senhaText = rememberTextFieldState()
    val confirmarSenhaText = rememberTextFieldState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
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
            state = emailText,
            placeholder = "Insira seu endereço de email",
            icon = R.drawable.alternate_email_24px,
            modifier = Modifier.fillMaxWidth()
        )
        AppPasswordField(
            state = senhaText,
            placeholder = "Crie uma senha",
            icon = R.drawable.lock_24px,
            modifier = Modifier.fillMaxWidth()
        )
        AppPasswordField(
            state = confirmarSenhaText,
            placeholder = "Repita a sua senha",
            icon = R.drawable.lock_24px,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = {}
        ) {
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = "Cadastrar",
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            text = termosString,
            textAlign = TextAlign.Center
        )
        Text(
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
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
        AuthScaffold { modifier ->
            SignInScreen(
                modifier = modifier,
                onNavigateToLogin = {}
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
        AuthScaffold { modifier ->
            SignInScreen(
                modifier = modifier,
                onNavigateToLogin = {}
            )
        }
    }
}