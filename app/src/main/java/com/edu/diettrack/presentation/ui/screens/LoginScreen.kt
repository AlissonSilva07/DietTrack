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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.edu.diettrack.R
import com.edu.diettrack.presentation.components.AppPasswordField
import com.edu.diettrack.presentation.components.AppTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignUp: () -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val logo = if (isDarkTheme) R.drawable.logo_white else R.drawable.logo_black

    val signUpTag = "sign_up"

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

    val cadastroString = buildAnnotatedString {
        append("Ainda não tem uma conta? ")

        pushStringAnnotation(
            tag = signUpTag,
            annotation = "navigate_to_sign_up"
        )
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Crie uma conta")
        }
        pop()

        append(".")
    }

    val emailText = rememberTextFieldState()
    val senhaText = rememberTextFieldState()

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
            text = "Boas vindas novamente!",
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
            placeholder = "Insira sua senha",
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
                text = "Entrar",
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
            text = cadastroString,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable(onClick = onNavigateToSignUp)
        )
    }
}