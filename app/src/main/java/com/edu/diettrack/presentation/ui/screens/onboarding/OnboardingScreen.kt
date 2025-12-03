package com.edu.diettrack.presentation.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.diettrack.R
import com.edu.diettrack.presentation.components.AppButton
import com.edu.diettrack.presentation.components.ButtonVariant
import com.edu.diettrack.presentation.components.FadeInItem
import com.edu.diettrack.presentation.navigation.OnboardingScaffold
import com.edu.diettrack.presentation.ui.theme.AppTheme

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val multipliedScreenWidth = screenWidth * 4

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {},
                shape = RoundedCornerShape(percent = 100),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Pular",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        OnboardingFinalStep(modifier = Modifier.weight(1f))
        FadeInItem(delay = 500L) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(unbounded = true),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.stripe),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .width(multipliedScreenWidth)
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppButton(
                title = "Começar",
                variant = ButtonVariant.DEFAULT,
                disabled = false,
                onClick = {},
                modifier = Modifier.width(140.dp),
                icon = null
            )
        }
    }
}

@Composable
fun OnboardingFinalStep(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FadeInItem(delay = 1000L) {
            Text(
                text = "Obrigado!",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(16.dp))
        FadeInItem(delay = 1500L) {
            Text(
                text = "Aproveite seu app",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(16.dp))
        FadeInItem(delay = 2000L) {
            Image(
                painter = painterResource(R.drawable.logo_color),
                contentDescription = null,
                modifier = Modifier.width(240.dp)
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        OnboardingScaffold { modifier ->
            OnboardingScreen(modifier)
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreviewDark() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        OnboardingScaffold { modifier ->
            OnboardingScreen(modifier)
        }
    }
}